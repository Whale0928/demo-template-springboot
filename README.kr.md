# Demo Template

[English Version](README.md)

스프링 부트 기반의 멀티모듈 템플릿입니다.

도메인 중심의 설계를 지향하면서도 실용적인 기본 구조를 제공합니다.

## 프로젝트 구조

```
root/
├── api/                # API 모듈 (웹 레이어, 실행 모듈)
├── core/
│   ├── base/          # 공통 유틸리티 (Pair 등)
│   └── domain/        # 도메인 로직 + JPA 엔티티 통합 관리
└── storage/
    └── rdb/           # 데이터베이스 설정 및 드라이버 관리
```

## 핵심 설계 원칙

### 1. 실용적 도메인 중심 설계

```
api -----> domain (JPA 통합) <----- base
  |
  └-----> storage/rdb (datasource)
```

- 도메인이 프로젝트의 중심
- 도메인에서 JPA 엔티티와 Repository를 함께 관리 (실용적 접근)
- storage는 순수 데이터소스 설정 및 드라이버 관리

### 2. 모듈별 역할

#### api

- 애플리케이션 진입점 (`Application.java`)
- REST API 컨트롤러 (`ApiController.java`)
- 실행 가능한 애플리케이션 (port: 8081)

#### core/domain

- JPA 엔티티 (`Version.java` - @EmbeddedId 복합키 사용)
- Repository 인터페이스 (`VersionRepository extends JpaRepository`)
- 비즈니스 서비스 (`VersionService.java`)
- JPA 관련 모든 로직 통합 관리

#### core/base

- 공통 유틸리티 (`Pair<F,S>` record 클래스)
- 도메인에서 필요한 기본 기능 제공

#### storage/rdb

- 데이터베이스별 설정 및 드라이버
- 커넥션 풀 관리
- 프로필별 데이터소스 분리

### 3. 기술 스택

- **Java**: 21
- **Spring Boot**: 3.4.0
- **Gradle**: 8.11.1
- **JPA**: Hibernate 6.6.2 + Spring Data JPA
- **Database**: H2 (개발), MySQL/PostgreSQL (운영)

## 데이터베이스 설정

### 개발 환경 (H2)

```yaml
# 기본 실행 - H2 인메모리 DB
  ./gradlew :api:bootRun

  # H2 웹 콘솔 접속
  http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:testdb
User:
  sa / Password: (공백)
```

### 운영 환경 (다중 데이터베이스 지원)

#### MySQL 설정

```bash
# 환경변수 설정
export DB_USERNAME=your_username
export DB_PASSWORD=your_password

# MySQL 프로필로 실행
./gradlew :api:bootRun --args='--spring.profiles.active=mysql'
```

#### PostgreSQL 설정

```bash
# PostgreSQL 프로필로 실행
./gradlew :api:bootRun --args='--spring.profiles.active=postgresql'
```

#### 다중 DB 지원 구조

- `application-rdb.yml`: H2 설정 (개발용)
- `application-mysql.yml`: MySQL 설정 (운영용)
- `application-postgresql.yml`: PostgreSQL 설정 (운영용)
- 프로필 기반 자동 전환

## 의존성 구조

### api/build.gradle

```gradle
dependencies {
    implementation(project(":storage:rdb"))  # 데이터소스 설정
    implementation project(':core:domain')   # 도메인 + JPA
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
}
```

### core/domain/build.gradle

```gradle
dependencies {
    api project(':core:base')              # base 유틸리티 노출
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}
```

### storage/rdb/build.gradle

```gradle
dependencies {
    implementation 'com.h2database:h2'              # H2 (개발용)
    // runtimeOnly 'com.mysql:mysql-connector-j'    # MySQL
    // runtimeOnly 'org.postgresql:postgresql'      # PostgreSQL
}
```

## API 엔드포인트

```http
GET /api
Response: Version 객체 (major, minor, patch)
```

## 주요 특징

### 1. 복합키 엔티티 설계

```java

@Entity
public class Version {
    @EmbeddedId
    private VersionId id;  // major, minor, patch 복합키

    public static Version versionStart() {
        return new Version(new VersionId(0, 0, 1));
    }
}
```

### 2. Spring Data JPA 자동 구현

- Repository 인터페이스만 정의
- JPA 구현체 자동 생성
- 트랜잭션 관리 자동화

### 3. 프로필 기반 환경 분리

- 개발: H2 인메모리 + 웹 콘솔
- 운영: MySQL/PostgreSQL + 프로덕션 설정
- 환경변수 기반 보안

### 4. 모듈 간 느슨한 결합

- 컴파일 시점과 런타임 의존성 분리
- 새로운 데이터베이스 추가 용이
- 도메인 로직과 인프라 분리

## 확장 가이드

### 새로운 데이터베이스 추가

1. `storage/rdb/build.gradle`에 드라이버 추가
2. `application-{db}.yml` 설정 파일 생성
3. 필요시 `{Database}Configuration.java` 추가

### 새로운 엔티티 추가

1. `core/domain/src/main/java/app/{domain}/` 패키지 생성
2. Entity, Repository, Service 클래스 작성
3. `@EntityScan` 범위에 자동 포함

## 개발 시작하기

```bash
# 1. 프로젝트 클론
git clone [repository-url]

# 2. 빌드 및 실행
./gradlew :api:bootRun

# 3. API 테스트
curl http://localhost:8081/api

# 4. H2 콘솔 접속
open http://localhost:8081/h2-console
```

이 템플릿은 도메인 주도 설계의 핵심 원칙을 따르면서도, 실용적인 구현이 가능한 구조를 제공합니다.
