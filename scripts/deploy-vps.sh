#!/usr/bin/env bash
set -euo pipefail

DEPLOY_PATH="${DEPLOY_PATH:-/opt/moodly-api}"
COMPOSE_DIR="${DEPLOY_PATH}/docker-compose"
DOCKERHUB_USERNAME="${DOCKERHUB_USERNAME:?DOCKERHUB_USERNAME is required}"
IMAGE_TAG="${IMAGE_TAG:-latest}"

cd "${DEPLOY_PATH}"

if [[ -d .git ]]; then
  git fetch origin main
  git reset --hard origin/main
fi

if [[ ! -f "${COMPOSE_DIR}/.env" ]]; then
  echo "ERROR: ${COMPOSE_DIR}/.env not found. Copy .env.example and configure secrets on the VPS first."
  exit 1
fi

export DOCKERHUB_USERNAME
export IMAGE_TAG

cd "${COMPOSE_DIR}"

docker compose \
  -f docker-compose.yml \
  -f docker-compose.prod.yml \
  --profile app \
  pull

docker compose \
  -f docker-compose.yml \
  -f docker-compose.prod.yml \
  --profile app \
  up -d --no-build --remove-orphans

echo "Waiting for Gateway health..."
for i in $(seq 1 60); do
  if curl -sf http://localhost:8072/actuator/health >/dev/null 2>&1; then
    echo "Gateway is UP"
    exit 0
  fi
  sleep 5
done

echo "ERROR: Gateway health check timed out"
docker compose -f docker-compose.yml --profile app ps
exit 1
