# Demo Template

스프링 부트 기반의 멀티모듈 템플릿. 실제 프로젝트에서 자주 사용되는 구조를 최소한으로 구성했습니다.

## 프로젝트 구조

```
root/
├── api/                # API 모듈 (웹 레이어)
├── core/
│   ├── base/          # 공통 유틸리티
│   └── domain/        # 순수한 도메인 로직
└── storage/
    └── rdb/           # DB 구현체
```

## 주요 설계 결정

### 1. 모듈 구성

- `api`: 실행 가능한 애플리케이션. web 의존성만 가지고 있음
- `core/base`: 공통 유틸리티. 다른 모듈들의 기반
- `core/domain`: 순수한 비즈니스 로직. JPA 어노테이션만 사용
- `storage/rdb`: 실제 DB 구현체. JPA 구현체를 여기서만 포함

### 2. 의존성 설계

```
api -> core/domain
    -> core/base
core/domain -> core/base
storage/rdb -> core/domain
           -> core/base
```

### 3. 모듈별 gradle 설정

#### api

```gradle
dependencies {
    implementation project(':core:base')
    implementation project(':core:domain')
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

#### core/base

```gradle
dependencies {
    implementation 'org.springframework:spring-context:6.2.0'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}
```

#### core/domain

```gradle
dependencies {
    implementation project(':core:base')
    implementation 'org.springframework:spring-context:6.2.0'
    compileOnly 'jakarta.persistence:jakarta.persistence-api:3.1.0'  // JPA 어노테이션만
}
```

#### storage/rdb

```gradle
dependencies {
    implementation project(':core:domain')
    implementation project(':core:base')
    implementation 'org.springframework:spring-context:6.2.0'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}
```

## 특징

### 도메인 순수성

- domain 모듈은 JPA 어노테이션만 사용하고 실제 구현체는 포함하지 않음
- 순수한 비즈니스 로직에 집중할 수 있는 구조

### 모듈 독립성

- 각 모듈은 자신의 역할에 충실한 최소한의 의존성만 포함
- base 모듈을 통해 공통 관심사 분리

### 유연한 확장

- storage 구현체를 쉽게 교체/확장 가능
- 새로운 저장소 추가가 용이한 구조

## 사용법

1. 이 템플릿을 clone
2. 프로젝트명 변경 (settings.gradle)
3. 필요한 의존성 추가
4. 구현 시작

이 템플릿은 실용성에 중점을 두고 설계됐습니다. 복잡한 설정 없이 바로 시작할 수 있으면서도, 확장이 필요할 때 쉽게 추가할 수 있는 구조를 지향합니다.
