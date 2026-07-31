#!/usr/bin/env bash
# 선착순 쿠폰 발급 수량·이력 초기화 (테스트 재실행 전)
set -euo pipefail

# 기본: coupon-service 직접(8087). IntelliJ local 또는 docker compose --profile app(8087 publish) 기준
HOST="${COUPON_HOST:-http://localhost:8087}"
URL="${HOST}/internal/coupon/load-test/flash/reset"

echo "POST ${URL}"
if ! curl -sS -X POST "${URL}" | python3 -m json.tool; then
  echo ""
  echo "연결 실패 시:"
  echo "  - docker compose --profile app up -d 후 moodly-coupon 컨테이너·8087 포트 확인"
  echo "  - docker compose ps && curl -s http://localhost:8087/actuator/health"
  exit 1
fi

echo ""
echo "초기화 완료. Locust 실행 전 issued_quantity=0, user_coupons 삭제됨."
