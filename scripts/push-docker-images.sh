#!/usr/bin/env bash
set -euo pipefail

DOCKERHUB_USERNAME="${DOCKERHUB_USERNAME:?DOCKERHUB_USERNAME is required}"
IMAGE_TAG="${IMAGE_TAG:?IMAGE_TAG is required}"

declare -A IMAGES=(
  ["configserver/Dockerfile"]="moodly-configserver"
  ["eurekaserver/Dockerfile"]="moodly-eurekaserver"
  ["auth-service/Dockerfile"]="moodly-auth"
  ["user-service/Dockerfile"]="moodly-user"
  ["product-service/Dockerfile"]="moodly-product"
  ["cart-service/Dockerfile"]="moodly-cart"
  ["order-service/Dockerfile"]="moodly-order"
  ["notification-service/Dockerfile"]="moodly-notification"
  ["coupon-service/Dockerfile"]="moodly-coupon"
  ["payment-service/Dockerfile"]="moodly-payment"
  ["gateway-service/Dockerfile"]="moodly-gateway"
)

for dockerfile in "${!IMAGES[@]}"; do
  image_name="${IMAGES[$dockerfile]}"
  full_image="${DOCKERHUB_USERNAME}/${image_name}"

  echo "Building ${full_image}:${IMAGE_TAG}"
  docker build -f "${dockerfile}" -t "${full_image}:${IMAGE_TAG}" -t "${full_image}:latest" .
  docker push "${full_image}:${IMAGE_TAG}"
  docker push "${full_image}:latest"
done

echo "All images pushed with tag: ${IMAGE_TAG}"
