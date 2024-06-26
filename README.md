# 구현 범위

-   카테고리 별 최저가격 브랜드와 상품 가격, 총액 조회 API

```
GET http://localhost:8080/api/goods/min-price
```

-   단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카티고리의 상품가격, 총액 조회 API

```
GET http://localhost:8080/api/goods/brand/min-price
```

-   카테고리 이름으로 최저,최고 가격 브랜드와 상품 가격 조회 API

```
GET http://localhost:8080/api/goods/category/{category}/min-max-price
```

-   상품 목록 조회 API

```
GET http://localhost:8080/admin/goods
```

-   브랜드 추가 API

```
POST http://localhost:8080/api/brands
```

-   브랜드 삭제 API

```
DELETE http://localhost:8080/api/brands/{brandName}
```

# 코드 빌드 방법

1. java 21 버전을 설치합니다
2. 아래 명령어를 입력하여 빌드 합니다

```bash
cd coordination/
./gradlew clean build -x test
```

# 실행 방법

1. 아래 명렬어를 이요하여 빌드된 jar 파일을 실행합니다

```bash
cd coordination/build/libs
java -jar coordination-0.0.1-SNAPSHOT.jar
```

2. dockerfile를 이용하여 실행합니다

```bash
cd coordination/
docker-compose up
```

# 테스트 방법

1. 웹 페이지를 통한 테스트 방법

-   위의 방법대로 웹 서버를 실행합니다
-   웹 브라우저에서 http://localhost:8080/ 로 접속합니다

2. 테스트 코드를 통한 테스트 방법

-   AdminControllerTest.java, CoordinationControllerTest.java 에 있는 단위 테스트로 해당 기능들을 테스트 할 수 있습니다

# ERD

```mermaid
erDiagram
    BRAND ||--o{ GOODS : has
    CATEGORY ||--o{ GOODS : has
    BRAND {
        BIGINT id
        VARCHAR(100) name
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
    GOODS{
        BIGINT id
        BIGINT brand_id
        BIGINT category_id
        INTEGER amount
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
    CATEGORY{
        BIGINT id
        VARCHAR(100) category_type
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
```
