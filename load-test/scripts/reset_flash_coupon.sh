#!/usr/bin/env bash
# 선착순 쿠폰 발급 수량·이력 초기화 (테스트 재실행 전)
set -euo pipefail

HOST="${COUPON_HOST:-http://localhost:8087}"
URL="${HOST}/internal/coupon/load-test/flash/reset"

echo "POST ${URL}"
curl -sS -X POST "${URL}" | python3 -m json.tool

echo ""
echo "초기화 완료. Locust 실행 전 issued_quantity=0, user_coupons 삭제됨."
