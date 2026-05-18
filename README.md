# Moodly API

Moodly 쇼핑몰 **백엔드 MSA** 모노레포입니다. Spring Boot 마이크로서비스, API Gateway, Eureka, Config Server, Docker Compose로 구성됩니다.

| 저장소 | 역할 |
|--------|------|
| **moodly-api** (본 저장소) | 마이크로서비스 소스, Gradle 빌드, Docker Compose |
| [moodly-config](https://github.com/YunTaeSeong/moodly-config) | Config Server용 중앙 YAML |
| [moodly-web](https://github.com/YunTaeSeong/moodly-web) | React 프론트엔드 |

---

## 데모 계정

프론트 http://localhost:3000 로그인 또는 API 로그인 테스트용입니다. **관리자 계정 1개**를 기준으로 문서화했습니다.

| 구분 | 이메일 | 비밀번호 | 권한 | 확인 가능 기능 |
|------|--------|----------|------|----------------|
| **관리자** | `admin@admin.com` | `admin1234!@#$` | ADMIN | 상품 문의·구매후기 **전체 조회**, 답변, 삭제 (상품상세·마이페이지) |
| **일반 회원** | 회원가입으로 생성 | 직접 설정 | USER | 장바구니, 주문, 후기 작성, 본인 문의 등 |

### ADMIN 권한은 어디에 있나?

로그인 응답 JSON에는 **`roles` 필드가 없습니다.** (`accessToken`, `refreshToken`만 반환)

| 단계 | 위치 | 내용 |
|------|------|------|
| 1. DB | **user-service** DB `users.role` | `ADMIN` 또는 `USER` (`UserRole` enum) |
| 2. 로그인 | auth → user `POST /internal/user/credential/verify` | DB `role` → `["ADMIN"]` 목록 조회 |
| 3. JWT | Access Token payload claim **`roles`** | 예: `"roles": ["ADMIN"]` ([jwt.io](https://jwt.io) 등으로 디코딩) |
| 4. Gateway | 요청 헤더 **`X-Roles`** | JWT에서 꺼내 하위 서비스로 전달 |
| 5. 백엔드 | `AuthPrincipal.isAdmin()` | `roles`에 `"ADMIN"` 포함 시 관리자 API 허용 |
| 6. 프론트 | `moodly-web` `isAdmin()` | 쿠키 `userEmail === 'admin@admin.com'` 이면 관리자 UI 표시 |

```bash
# Gateway 로그인 스모크 테스트 (응답에 accessToken 포함)
curl -s -X POST http://localhost:8072/AUTH-SERVICE/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@admin.com","password":"admin1234!@#$"}'

# role 확인 (user-service 내부 API, userId는 로그인 JWT의 sub)
curl -s http://localhost:8082/internal/user/1/roles
# → ["ADMIN"]
```

> 최초 DB가 비어 있으면 `POST /USER-SERVICE/user/register`로 계정 생성 후, **user DB** `users` 테이블에서 `role = 'ADMIN'`으로 변경하세요. DB 구조 참고: `moodly-web/database/schema.sql`

---

## 기술 스택

| 구분         | 기술                                                              |
|------------|-----------------------------------------------------------------|
| Runtime    | Java 21, Spring Boot 3.5.x, Spring Cloud 2025.0.x               |
| MSA        | Spring Cloud Gateway (WebFlux), Eureka, Config Server, OpenFeign |
| Data       | JPA, QueryDSL, MySQL 8, Redis, Kafka                            |
| Resilience | Resilience4j (Circuit Breaker, Retry, Rate Limiter)             |
| Security   | JWT, Spring Security                                            |
| Infra      | Docker Compose                                                  |
| AI         | ChatGpt, Cursor                                                 |

카카오 로그인, 토스 결제, 네이버 메일은 **선택 기능**입니다. 키 없이도 대부분의 서비스는 기동할 수 있습니다.

**사용하려면** `docker-compose/.env`(또는 IDE 환경 변수)와 프론트 `.env`에 아래 값을 설정한 뒤 해당 서비스를 재시작하세요.

| 기능 | 백엔드 (`docker-compose/.env`) | 프론트 (`moodly-web/.env`) | 비고 |
|------|-------------------------------|----------------------------|------|
| 카카오 로그인 | `KAKAO_CLIENT_ID`, `KAKAO_CLIENT_SECRET`, `KAKAO_REDIRECT_URI` | `REACT_APP_KAKAO_CLIENT_ID`, `REACT_APP_KAKAO_REDIRECT_URI` | [카카오 개발자](https://developers.kakao.com)에서 REST API 키·Redirect URI 등록. 백·프론트 Redirect URI **동일**해야 함 |
| 토스 결제 | `TOSS_SECRET_KEY` (`test_sk_...`) | `REACT_APP_TOSS_CLIENT_KEY` (`test_ck_...`) | [개발자센터 API 키](https://developers.tosspayments.com/my/api-keys)에서 **같은 상점·테스트 세트**로 복사.
| 네이버 메일 (ID/PW 찾기) | `NAVER_MAIL_USERNAME`, `NAVER_MAIL_PASSWORD` | — | 네이버 **앱 비밀번호** 사용 (일반 로그인 비밀번호 아님) |

---

## 아키텍처

```text
moodly-web (:3000)
       │
       ▼
Gateway (:8072)  ← JWT, RateLimiter, CircuitBreaker, CORS
       │
       ▼ (Eureka lb://)
auth │ user │ product │ cart │ order │ payment │ coupon │ notification
       │
       ▼
MySQL (서비스별 DB)   Redis / Kafka
```

| 구성요소 | 포트 | 설명 |
|----------|------|------|
| Gateway | **8072** | 브라우저·프론트 **유일 진입점** |
| Config Server | 8071 | `moodly-config` Git 저장소 연동 |
| Eureka | 8070 | 서비스 디스커버리 |
| auth ~ payment | 8081~8088 | Docker `app` 프로필 시 호스트 미노출 (`expose`만) |
| MySQL | 3310~3317 | 호스트에서 DB 직접 접속용 |

Gateway 호출 예: `POST http://localhost:8072/AUTH-SERVICE/auth/login`

---

## 인증·권한 공통

| 계층 | 동작 |
|------|------|
| **Gateway (8072)** | JWT 검증 → `X-User-Id`, `X-Roles` 헤더 주입. `permit-all` 경로만 비로그인 허용 |
| **각 마이크로서비스** | `JwtAuthenticationFilter`로 `AuthPrincipal` 설정 |
| **ADMIN** | JWT `roles`에 `"ADMIN"` 포함 시 `AuthPrincipal.isAdmin()` |
| **관리자 API** | SecurityConfig는 대부분 `authenticated()`만 요구 → **실제 ADMIN 검증은 서비스 `isAdmin()`** |

내부 API (`/internal/**`)는 Gateway·서비스 모두 JWT 없이 통과 → **Feign 서비스 간 호출 전용**입니다.

---

## 서비스별 기능 상세

### gateway-service (8072)

| 기능 | 설명 |
|------|------|
| API 라우팅 | `/{SERVICE-NAME}/**` → Eureka `lb://` 각 마이크로서비스 |
| JWT | 로그인·회원가입·상품 GET·후기 목록 등 `permit-all`, 나머지 Bearer 필수 |
| Resilience | auth RateLimiter, product GET Retry, auth/product/order/payment/coupon Circuit Breaker |
| CORS | `http://localhost:3000` 허용, `DedupeResponseHeader`로 중복 헤더 제거 |
| Fallback | CB 발동 시 `503` + `/fallback/{service}` |

---

### auth-service (8081)

| API | 경로 | 권한 |
|-----|------|------|
| 이메일 로그인 | `POST /auth/login` | 공개 |
| 카카오 로그인 | `POST /auth/kakao/login` | 공개 |
| 토큰 갱신 | `POST /auth/refresh` | 공개 |
| 로그아웃 | `POST /auth/logout` | 공개 (Refresh 삭제) |
| Refresh 폐기 | `DELETE /auth/refresh-token/{userId}` | 인증 (user-service에서 비밀번호 변경 시 호출) |

**비즈니스 규칙**

- 로그인: `user-service`에서 이메일·비밀번호 검증 후 Access/Refresh JWT 발급 (`roles` 포함)
- 카카오: 카카오 토큰 → 사용자 정보 → `user-service` findOrCreate → JWT
- Refresh 토큰은 DB 저장, 로그아웃·비밀번호 변경 시 폐기

**Feign:** `user-service` — 자격 증명 검증, 카카오 사용자 생성, roles 조회

---

### user-service (8082)

| API | 경로 | 권한 |
|-----|------|------|
| 회원가입 | `POST /user/register` | 공개 |
| 이메일 중복 확인 | `GET /user/email-available` | 공개 |
| 아이디 찾기 | `POST /user/find-id/request`, `/confirm` | 공개 |
| 비밀번호 재설정 | `POST /user/password/reset/request`, `/confirm` | 공개 |
| 비밀번호 변경 | `PATCH /user/mypage/security/changePassword` | 로그인 |
| 배송지 CRUD | `/user/delivery-addresses/**` | 로그인 (본인만) |
| 기본 배송지 설정 | `PATCH /user/delivery-addresses/{id}/default` | 로그인 (본인만) |

**비즈니스 규칙**

- 회원가입: 비밀번호·비밀번호 확인 일치, 이메일 중복 불가
- 아이디 찾기: 이름+전화 일치 시 Redis(5분) + 이메일 인증코드, confirm 1회성
- 비밀번호 재설정: 이메일 존재 시에만 링크 발송(존재 여부 노출 최소화), Redis 15분
- 비밀번호 변경: 현재 비밀번호 검증 → 변경 후 **auth Refresh 전체 폐기**
- 배송지: 본인 `userId`만 CRUD, 기본 배송지 설정 시 기존 default 해제

**Feign:** `auth-service` — 비밀번호 변경 후 Refresh 폐기

---

### product-service (8083)

#### 상품·카테고리·찜

| API | 권한 | 비즈니스 규칙 |
|-----|------|---------------|
| `GET /product/**` (목록·상세·검색·핫딜·오늘의특가) | 공개 | — |
| `GET /category/**` | 공개 | — |
| `POST/DELETE/GET /wishlist/**` | 로그인 | 본인 찜만, 중복 찜 불가, 없는 상품 예외 |

#### 상품 문의

| 역할 | 조회 | 작성 | 수정 | 삭제 | 답변 |
|------|------|------|------|------|------|
| **일반 사용자** | **본인 문의만** (`GET /product/inquiry/all`, 단건) | 로그인, **구매 불필요** | **미답변만** | **미답변만** | 불가 |
| **관리자 (ADMIN)** | **전체** (`GET /product/admin/inquiry`) | — | **답변 후에도 가능** | **답변 후에도 가능** | 등록 시 `COMPLETED` |

**사용자 API** (`/product/inquiry`)

| 메서드 | 경로 | 조건 |
|--------|------|------|
| POST | `/{productId}` | 로그인, 상품 존재 |
| GET | `/{productInquiryId}` | 본인 문의만 |
| GET | `/all` | 본인 문의만 (productId·status·content 필터) |
| PATCH | `/{id}/update` | 본인 + **`reply` 없음 + status ≠ COMPLETED** |
| DELETE | `/{id}/delete` | 본인 + **`reply` 없음 + status ≠ COMPLETED** |

**관리자 API** (`/product/admin/inquiry`) — JWT + `isAdmin()`

| 메서드 | 경로 | 조건 |
|--------|------|------|
| GET | `/` | 전체 문의 검색 |
| POST | `/{id}/reply` | 답변 등록 → status `COMPLETED`, Kafka 알림(작성자) |
| PATCH | `/{id}` | 문의 내용 수정 (답변 여부 무관) |
| DELETE | `/{id}` | 문의 삭제 (답변 여부 무관) |

> 답변이 달린 뒤에는 **사용자는 수정·삭제 불가** (`INQUIRY_ALREADY_REPLIED`). 관리자는 계속 수정·삭제 가능.

#### 구매후기

| 역할 | 조회 | 작성 | 수정 | 삭제 | 답변 |
|------|------|------|------|------|------|
| **일반 사용자** | 상품별 목록 **공개**, 내 목록은 본인 | 결제완료 등 주문 상태 + 본인 주문 | API 없음 | API 없음 | 불가 |
| **관리자 (ADMIN)** | 전체 목록 | — | — | 가능 | 1회만 (중복 시 예외) |

**작성 조건**

- 로그인 필수
- **주문 항목(`orderItemId`)당 1건** (`REVIEW_ALREADY_EXISTS`)
- `order-service` Feign으로 검증: 본인 주문, `productId` 일치, 주문 상태 ∈ `PAYMENT_COMPLETED`, `PREPARING_SHIPMENT`, `SHIPPED`, `DELIVERED`
- 첨부 이미지 **최대 3장**

**사용자 API** (`/product/review`)

| 메서드 | 경로 | 권한 |
|--------|------|------|
| POST | `/` | 로그인 |
| GET | `/product/{productId}` | **공개** |
| GET | `/my` | 로그인 (본인) |
| GET | `/exists?orderItemId=` | 로그인 |

**관리자 API** (`/product/admin/review`)

| 메서드 | 경로 | 서비스 검증 |
|--------|------|-------------|
| GET | `/` | JWT만 (목록은 `isAdmin()` 미검증 — **ADMIN 계정으로 호출 권장**) |
| POST | `/{reviewId}/reply` | `isAdmin()`, 이미 답변 있으면 `REVIEW_ALREADY_REPLIED` |
| DELETE | `/{reviewId}` | `isAdmin()` |

> 구매후기는 **사용자 수정·삭제 API 없음**. 관리자만 삭제·답변 가능.

**Feign:** `order-service` — `GET /internal/order/item/{orderItemId}/review-eligibility`  
**이벤트:** 문의 생성/답변 → Kafka `inquiry-events` → notification-service

---

### cart-service (8084)

| API | 권한 | 비즈니스 규칙 |
|-----|------|---------------|
| `POST /cart` | 로그인 | 본인 장바구니, 동일 상품 시 수량 합산 |
| `GET /cart` | 로그인 | 본인 장바구니만 |
| `PATCH` 수량·체크 | 로그인 | 본인만, 수량 최소 1 |
| `DELETE` 단건·선택 삭제 | 로그인 | 본인만 |

**Feign:** `product-service` — 상품 정보 조회 (실패 시 null 상품으로 표시)

---

### order-service (8085)

| API | 권한 | 비즈니스 규칙 |
|-----|------|---------------|
| `POST /order` | 로그인 | 장바구니 항목으로 주문 생성, **모든 cart의 userId = 주문자**, 할인·배송비(15,000원 이상 무료), 상태 `PENDING_PAYMENT`, 완료 후 cart 삭제 |
| `GET /order/{id}` | 로그인 | **본인 주문만** |
| `GET /order/all` | 로그인 | 본인 주문 전체 (최신순) |
| `DELETE /order/{id}` | 로그인 | 본인 + **`PENDING_PAYMENT`만** 삭제 |
| `DELETE /order/all` | 로그인 | 본인 주문 **전체** 삭제 (상태 제한 없음) |

**내부 API** (`/internal/order`) — payment·product Feign 전용

| API | 용도 |
|-----|------|
| 결제 스냅샷 | payment-service 승인 전 주문 조회 |
| 결제 완료/취소 | Toss 결과 반영 (멱등) |
| 후기 자격 | `GET /item/{orderItemId}/review-eligibility` |

**Feign:** `cart-service` — 항목 조회·삭제

---

### payment-service (8088)

| API | 권한 | 비즈니스 규칙 |
|-----|------|---------------|
| `POST /payment/confirm` | 로그인 | 본인 주문, 금액 일치, 배송지 필수, **`PENDING_PAYMENT`만**, 쿠폰 검증, Toss 승인 → `PAYMENT_COMPLETED` (멱등) |
| `POST /payment/cancel` | 로그인 | 본인, **`PAYMENT_COMPLETED`만**, Toss 취소 + `PAYMENT_CANCELLED` |

**Feign:** `order-service`, `coupon-service` (결제 전 쿠폰 검증)

---

### coupon-service (8087)

| API | 권한 | 비즈니스 규칙 |
|-----|------|---------------|
| `GET /coupon/receivable` | 로그인 | 받을 수 있는 쿠폰 목록 (신규회원 10% 포함) |
| `POST /coupon/{couponId}/issue` | 로그인 | 쿠폰 **받기** → `user_coupons` INSERT |
| 내 쿠폰 조회·사용·취소·만료 | 로그인 | **본인 `userCoupon`만** |
| `POST /internal/admin/coupon` | 내부/관리자 | `isAdmin()` 쿠폰 생성 |
| `POST /internal/coupon/users/{userId}/welcome` | 내부 (선택) | 가입 직후 자동 발급용 API — **현재 미연결** |
| 결제 검증 | 내부 | 최소 주문금액 등 `assertPayableBeforeCharge` |

#### 신규회원 10% 할인 쿠폰 (발급 방식)

회원가입 직후 **자동 지급되지 않습니다.** 마이페이지에서 사용자가 **「받기」** 할 때 발급됩니다.

| 단계 | 코드 위치 | 설명 |
|:----:|-----------|------|
| 1 | `CouponDataInitializer` | coupon-service 기동 시 `coupons` 테이블에 마스터 **「신규회원 10% 할인」** 등록 (10%, 최소 주문 15,000원, 발급 후 30일) |
| 2 | `GET /coupon/receivable` · `CouponService.getReceivableCoupons()` | 아직 없으면 받을 수 있는 목록에 노출 |
| 3 | 프론트 마이페이지 · `POST /coupon/{couponId}/issue` · `CouponService.issue()` | **받기** 클릭 시 `user_coupons`에 저장 |

> `UserService.register`(회원가입)에는 쿠폰 호출이 없습니다. 자동 발급 API(`issueWelcomeCouponForNewUser`)는 있으나 user-service와 연동되어 있지 않습니다.

---

### notification-service (8086)

| API | 권한 | 비즈니스 규칙 |
|-----|------|---------------|
| `GET /notification/**` | **전부 공개** (JWT 없음) | `userId` 쿼리 파라미터로 식별 |
| SSE | 공개 | 실시간 푸시 |
| 읽음·삭제 | 공개 | 해당 `userId` 소유 알림만 |

**Kafka `inquiry-events`**

- 문의 **생성** → ADMIN 전원 알림 (`user-service`에서 admin userId 목록 조회)
- 문의 **답변** → 문의 작성자 알림

---

## 도메인 규칙 요약

| 도메인 | 사용자 | 관리자 |
|--------|--------|--------|
| **상품 문의** | 본인 것만 조회·수정·삭제, **답변 전만** 수정/삭제 | 전체 조회, 답변·수정·삭제 **항상 가능** |
| **구매후기** | 결제완료 등 주문만 작성, 상품별 목록 공개, **수정/삭제 없음** | 전체 조회, 답변 1회, 삭제 |
| **주문** | 본인만, 결제 대기만 단건 삭제 | — |
| **장바구니·배송지·쿠폰** | 본인 데이터만 | 쿠폰 생성 등 일부 ADMIN API |

---

## 서비스 간 호출 (Feign)

```text
auth-service      → user-service
user-service      → auth-service
cart-service      → product-service
order-service     → cart-service
payment-service   → order-service, coupon-service
product-service   → order-service
product-service   → Kafka → notification-service
notification      → user-service (HTTP, admin userId 목록)
```

공통 모듈: `common-core`, `common-security`

---

## 사전 요구사항

- JDK **21**
- Docker / Docker Compose
- Git (`moodly-config` clone용)

---

## 실행 방식

| 방식 | 백엔드 | 환경 변수 | 프론트 API 주소 |
|------|--------|-----------|-----------------|
| **A. 전체 Docker** (권장) | `docker compose --profile app up -d` | `docker-compose/.env` **한 파일** → Compose가 각 컨테이너에 자동 주입 | Gateway `http://localhost:8072/{SERVICE-NAME}` |
| **B. 하이브리드** (IDE 디버깅) | `docker compose up -d` + IDE `local` | **서비스 모듈마다** Run Configuration에 env 설정 필요 | 서비스 직접 `http://localhost:8081` 등 |

**A**를 사용할 때 프론트는 반드시 Gateway URL이어야 합니다. `8081`로 호출하면 `ERR_CONNECTION_REFUSED`가 발생합니다.

→ 프론트 설정: [moodly-web README](https://github.com/YunTaeSeong/moodly-web/blob/main/README.md)

---

## A. 전체 Docker 실행 (권장)

### 1. 환경 변수

```bash
cd docker-compose
cp .env.example .env
# KEY=value 형식으로 수정 (KEY: value 는 Docker에서 인식되지 않음)
```

`docker compose`는 **`docker-compose/` 디렉터리의 `.env` 파일을 자동으로 읽습니다.**  
`docker-compose.yml`에 `${DB_PASSWORD}`, `${JWT_SECRET}` 등으로 선언된 값이 **auth, user, gateway 등 각 컨테이너 `environment`에 알아서 채워집니다.**  
따라서 `.env`만 준비하면 서비스마다 env를 따로 넣을 필요가 없습니다.

| 변수 | 필수 | 설명 |
|------|:----:|------|
| `MYSQL_ROOT_PASSWORD`, `DB_USERNAME`, `DB_PASSWORD` | ✅ | MySQL |
| `JWT_SECRET`, `JWT_ACCESS_TOKEN`, `JWT_REFRESH_TOKEN` | ✅ | Gateway·모든 서비스 **동일 값** |
| `KAKAO_*` | △ | 카카오 로그인 |
| `TOSS_SECRET_KEY` | △ | 결제 |
| `NAVER_MAIL_*` | △ | 비밀번호 찾기 메일 |
| `KAFKA_ENABLED` | △ | Kafka 사용 여부 |

전체 목록: [`docker-compose/.env.example`](docker-compose/.env.example)

### 2. 빌드 및 기동

```bash
# moodly-api 루트
./gradlew clean build -x test

cd docker-compose
docker compose up -d                         # Config, Eureka, Redis, Kafka, MySQL
docker compose --profile app up -d --build   # MSA + Gateway
```

### 3. 확인

```bash
docker compose ps
curl http://localhost:8072/actuator/health
# Eureka: http://localhost:8070

curl -s -X POST http://localhost:8072/AUTH-SERVICE/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@admin.com","password":"admin1234!@#$"}'
```

### 4. 프론트 연동

```bash
cd ../moodly-web
cp .env.docker .env
npm install
npm run start:docker
```

http://localhost:3000

### 5. 종료

```bash
cd moodly-api/docker-compose
docker compose --profile app down
docker compose down
```

---

## B. 로컬 IDE 실행 (하이브리드)

인프라만 Docker, 애플리케이션은 IDE에서 `local` 프로필로 실행합니다.

```bash
cd docker-compose
cp .env.example .env
docker compose up -d    # app 프로필 없음 — Config, Eureka, Redis, Kafka, MySQL만 기동
```

`docker compose up -d`(**`app` 프로필 없음**)는 **MSA 앱 컨테이너를 띄우지 않습니다.**  
MySQL·Redis 등 인프라 컨테이너만 `.env`로 설정되고, **auth-service, user-service 등은 IDE에서 직접 실행**합니다.

이때 **각 서비스 모듈의 Run Configuration마다 환경 변수를 넣어야 합니다.** Docker Compose가 앱 JVM까지 대신 채워 주지 않습니다.

| 순서 | 모듈 | Profile |
|:----:|------|---------|
| 1 | configserver | 기본 |
| 2 | eurekaserver | — |
| 3 | auth ~ payment, notification | `local` |
| 4 | gateway-service | `local` |

**IDE 설정 예 (모듈마다 반복)**

- **공통:** `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `JWT_ACCESS_TOKEN`, `JWT_REFRESH_TOKEN`
- **auth-service 추가:** `KAKAO_CLIENT_ID`, `KAKAO_CLIENT_SECRET`, `KAKAO_REDIRECT_URI`
- **user-service 추가:** `NAVER_MAIL_USERNAME`, `NAVER_MAIL_PASSWORD`
- **payment-service 추가:** `TOSS_SECRET_KEY`

IntelliJ: Run/Debug Configuration → Environment variables → `docker-compose/.env` 내용 Import, 또는 `.env` 파일 경로 지정.

DB·Redis URL은 `moodly-config`의 `*-local.yml`에 정의되어 있어, 위 변수만 맞으면 `localhost:3310` 등으로 연결됩니다.

로컬 MySQL 호스트 포트: auth `3310` … payment `3317` ([moodly-config README](https://github.com/YunTaeSeong/moodly-config/blob/main/README.md) 참고)

---

## Config Server

- 기본 Git URI: `https://github.com/YunTaeSeong/moodly-config.git`
- Docker 컨테이너: `SPRING_CLOUD_CONFIG_URI=http://moodly-configserver:8071`
- `moodly-config` 수정 후 push → 해당 서비스 재시작 시 반영

---

## 프로젝트 구조

```text
moodly-api/
├── common-core/              # 공통 예외, BaseEntity
├── common-security/          # JWT 필터, Security 공통
├── configserver/
├── eurekaserver/
├── gateway-service/
├── auth-service/
├── user-service/
├── product-service/
├── cart-service/
├── order-service/
├── payment-service/
├── coupon-service/
├── notification-service/
└── docker-compose/           # compose, .env.example
```

---

## 테스트

```bash
./gradlew test
```

---

## 트러블슈팅

| 증상 | 해결 |
|------|------|
| `--profile app`인데 `ERR_CONNECTION_REFUSED` | 프론트가 `8081` 호출 중 → `npm run start:docker` 또는 Gateway URL 설정 |
| CORS `Allow-Origin` 중복 | Gateway `globalcors` + MSA CORS 중복 → auth·gateway 이미지 재빌드 |
| Gateway `503` | 기동 직후 Circuit Breaker → 30초~1분 후 재시도 |
| Gateway `429` | AUTH Rate Limiter 정상 동작 |
| Config 읽기 실패 | Config Server(8071) 기동, `moodly-config` 접근 가능 여부 |
| JWT 401 | `JWT_SECRET` Gateway·서비스 동일 여부 |

---

## 관련 문서

- [moodly-config README](https://github.com/YunTaeSeong/moodly-config/blob/main/README.md)
- [moodly-web README](https://github.com/YunTaeSeong/moodly-web/blob/main/README.md)
