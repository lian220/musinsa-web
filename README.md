# 무신사 API

## 개요

**무신사 API**는 무신사의 코디 완성 서비스를 구현하기 위한 API 명세서입니다. 고객이 다양한 카테고리의 상품을 선택하여 최적의 코디를 완성할 수 있도록 지원하는 기능을 제공합니다. 또한 운영자는 새로운 브랜드를 관리할 수 있습니다.

---

# 실행
```bash
docker-compose up -d --build
```

# Swagger 문서
- http://localhost:8080/swagger-ui/index.html#/

# API 목차

## 1. 카테고리 별 최저가격 브랜드 및 총액 조회 API
- **엔드포인트:** `/api/categories/min-prices`
- **메소드:** `GET`

## 2. 단일 브랜드로 전체 카테고리 상품 구매 시 최저가격 조회 API
- **엔드포인트:** `/api/brands/lowest-total`
- **메소드:** `GET`

## 3. 특정 카테고리의 최저 및 최고 가격 브랜드 조회 API
- **엔드포인트:** `/api/categories/{categoryName}/price-range`
- **메소드:** `GET`

## 4. 브랜드 및 상품 관리 API
- **엔드포인트:** `/api/brands`
- **메소드:** `POST`, `PUT`, `DELETE`

---


## 사전 요구사항

애플리케이션을 실행하기 전에 다음 요구사항을 충족해야 합니다:

- **Docker**: [Docker 설치하기](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Docker Compose 설치하기](https://docs.docker.com/compose/install/)
- **Git**: [Git 설치하기](https://git-scm.com/downloads)

## 설치

1. **리포지토리 클론**

   먼저, 리포지토리를 클론하고 해당 디렉토리로 이동합니다.

   ```bash
   git clone https://github.com/your-username/clustering-api.git
   cd clustering-api
