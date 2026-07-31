"""
선착순 70% 쿠폰(1000명) Locust 부하 테스트

사전 조건:
  - docker-compose (db, redis, config, eureka) + coupon-service 기동
  - moodly.coupon.load-test.enabled=true (coupon-service-local/docker.yml)

실행 예:
  cd load-test
  pip install -r requirements.txt
  ./scripts/reset_flash_coupon.sh
  locust -f locustfile.py --host=http://localhost:8087 \\
    --users 1000 --spawn-rate 200 --run-time 30s --headless
"""

from __future__ import annotations

import json
import os
from threading import Lock
from typing import Optional

from locust import HttpUser, between, events, task


USER_ID_LOCK = Lock()
USER_ID_SEQ = 0

ISSUE_PATH = "/internal/coupon/load-test/flash/issue"
STATUS_PATH = "/coupon/flash/status"

# 커스텀 집계 (Locust UI Statistics 외 참고용)
METRICS = {
    "issue_success": 0,
    "issue_exhausted": 0,
    "issue_duplicate": 0,
    "issue_other_error": 0,
    "status_ok": 0,
}
METRICS_LOCK = Lock()


def _next_user_id() -> int:
    global USER_ID_SEQ
    with USER_ID_LOCK:
        USER_ID_SEQ += 1
        return USER_ID_SEQ


def _parse_error_code(response) -> Optional[str]:
    try:
        payload = response.json()
    except json.JSONDecodeError:
        return None
    body = payload.get("body") if isinstance(payload, dict) else None
    if isinstance(body, dict) and body.get("code"):
        return body["code"]
    if isinstance(payload, dict) and payload.get("code"):
        return payload["code"]
    return None


def _record(metric_key: str) -> None:
    with METRICS_LOCK:
        METRICS[metric_key] = METRICS.get(metric_key, 0) + 1


class FlashCouponRaceUser(HttpUser):
    """가상 사용자 1명 = userId 1개 → 선착순 쿠폰 1회 발급 시도"""

    wait_time = between(0, 0.05)

    def on_start(self) -> None:
        self.load_test_user_id = _next_user_id()

    @task(10)
    def issue_flash_coupon(self) -> None:
        with self.client.post(
            ISSUE_PATH,
            params={"userId": self.load_test_user_id},
            name="POST flash/issue",
            catch_response=True,
        ) as response:
            if response.status_code == 200:
                _record("issue_success")
                response.success()
                return

            code = _parse_error_code(response)
            if response.status_code == 400 and code == "COUPON_007":
                _record("issue_exhausted")
                response.success()
                return

            if response.status_code == 400 and code == "COUPON_0012":
                _record("issue_duplicate")
                response.success()
                return

            _record("issue_other_error")
            response.failure(f"status={response.status_code}, code={code}")

    @task(1)
    def poll_flash_status(self) -> None:
        with self.client.get(
            STATUS_PATH,
            name="GET flash/status",
            catch_response=True,
        ) as response:
            if response.status_code == 200:
                _record("status_ok")
                response.success()
            else:
                response.failure(f"status={response.status_code}")


@events.test_start.add_listener
def on_test_start(environment, **kwargs) -> None:
    global USER_ID_SEQ
    with USER_ID_LOCK:
        USER_ID_SEQ = int(os.getenv("LOCUST_USER_ID_START", "0"))

    host = environment.host or "http://localhost:8087"
    print(f"[locust] host={host}")
    print(f"[locust] issue endpoint={ISSUE_PATH}")
    print(f"[locust] status endpoint={STATUS_PATH}")


@events.test_stop.add_listener
def on_test_stop(environment, **kwargs) -> None:
    with METRICS_LOCK:
        snapshot = dict(METRICS)

    print("\n========== 선착순 쿠폰 부하 테스트 요약 ==========")
    print(f"  발급 성공(200):     {snapshot.get('issue_success', 0)}")
    print(f"  수량 마감(007):     {snapshot.get('issue_exhausted', 0)}")
    print(f"  중복 발급(0012):    {snapshot.get('issue_duplicate', 0)}")
    print(f"  기타 실패:          {snapshot.get('issue_other_error', 0)}")
    print(f"  status 조회 성공:   {snapshot.get('status_ok', 0)}")
    print("================================================")
    print("검증: load-test/README.md 의 SQL·curl 절 참고")
