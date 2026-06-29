#!/usr/bin/env bash
set -euo pipefail

# VPS 최초 1회 실행 — Docker, Git, 배포 디렉터리 준비
# 예: curl -fsSL ... | bash  또는 repo clone 후 bash scripts/vps-bootstrap.sh

DEPLOY_PATH="${DEPLOY_PATH:-/opt/moodly-api}"
REPO_URL="${REPO_URL:-https://github.com/YunTaeSeong/moodly-api.git}"

if ! command -v docker >/dev/null 2>&1; then
  echo "Installing Docker..."
  curl -fsSL https://get.docker.com | sh
  systemctl enable docker
  systemctl start docker
fi

if ! docker compose version >/dev/null 2>&1; then
  echo "ERROR: docker compose plugin is required."
  exit 1
fi

mkdir -p "$(dirname "${DEPLOY_PATH}")"

if [[ ! -d "${DEPLOY_PATH}/.git" ]]; then
  git clone "${REPO_URL}" "${DEPLOY_PATH}"
fi

cd "${DEPLOY_PATH}/docker-compose"

if [[ ! -f .env ]]; then
  cp .env.example .env
  echo ""
  echo "Created ${DEPLOY_PATH}/docker-compose/.env"
  echo "Edit JWT, DB password, optional API keys before first deploy."
fi

chmod +x "${DEPLOY_PATH}/scripts/deploy-vps.sh"

echo "Bootstrap complete."
echo "Next:"
echo "  1) Edit ${DEPLOY_PATH}/docker-compose/.env"
echo "  2) Configure GitHub Secrets (DOCKERHUB_*, VPS_*)"
echo "  3) Push to main or run CD workflow"
