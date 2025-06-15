# Demo Template

[한글 버전](README.kr.md)

A Spring Boot based multi-module template project.

This template provides a practical basic structure that follows domain-driven design principles.

## Project Structure

```
root/
├── api/                # API module (web layer, executable module)
├── core/
│   ├── base/          # Common utilities (Pair, etc.)
│   └── domain/        # Domain logic + JPA entity integration
└── storage/
    └── rdb/           # Database configuration and driver management
```

## Core Design Principles

### 1. Practical Domain-Centered Design

```
api -----> domain (JPA integrated) <----- base
  |
  └-----> storage/rdb (datasource)
```

- Domain is the center of the project
- Domain manages JPA entities and repositories together (practical approach)
- Storage handles pure datasource configuration and driver management

### 2. Module Responsibilities

#### api

- Application entry point (`Application.java`)
- REST API controllers (`ApiController.java`)
- Executable application (port: 8081)

#### core/domain

- JPA entities (`Version.java` - using @EmbeddedId composite key)
- Repository interfaces (`VersionRepository extends JpaRepository`)
- Business services (`VersionService.java`)
- Integrated management of all JPA-related logic

#### core/base

- Common utilities (`Pair<F,S>` record class)
- Basic functionality needed by domain

#### storage/rdb

- Database-specific configuration and drivers
- Connection pool management
- Profile-based datasource separation

### 3. Technology Stack

- **Java**: 21
- **Spring Boot**: 3.4.0
- **Gradle**: 8.11.1
- **JPA**: Hibernate 6.6.2 + Spring Data JPA
- **Database**: H2 (development), MySQL/PostgreSQL (production)

## Database Configuration

### Development Environment (H2)

```yaml
# Default execution - H2 in-memory DB
  ./gradlew :api:bootRun

  # H2 Web Console access
  http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:testdb
User:
  sa / Password: (empty)
```

### Production Environment (Multi-database Support)

#### MySQL Configuration

```bash
# Environment variables
export DB_USERNAME=your_username
export DB_PASSWORD=your_password

# Run with MySQL profile
./gradlew :api:bootRun --args='--spring.profiles.active=mysql'
```

#### PostgreSQL Configuration

```bash
# Run with PostgreSQL profile
./gradlew :api:bootRun --args='--spring.profiles.active=postgresql'
```

#### Multi-DB Support Structure

- `application-rdb.yml`: H2 configuration (development)
- `application-mysql.yml`: MySQL configuration (production)
- `application-postgresql.yml`: PostgreSQL configuration (production)
- Profile-based automatic switching

## Dependency Structure

### api/build.gradle

```gradle
dependencies {
    implementation(project(":storage:rdb"))  # Datasource configuration
    implementation project(':core:domain')   # Domain + JPA
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
}
```

### core/domain/build.gradle

```gradle
dependencies {
    api project(':core:base')              # Expose base utilities
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}
```

### storage/rdb/build.gradle

```gradle
dependencies {
    implementation 'com.h2database:h2'              # H2 (development)
    // runtimeOnly 'com.mysql:mysql-connector-j'    # MySQL
    // runtimeOnly 'org.postgresql:postgresql'      # PostgreSQL
}
```

## API Endpoints

```http
GET /api
Response: Version object (major, minor, patch)
```

## Key Features

### 1. Composite Key Entity Design

```java

@Entity
public class Version {
    @EmbeddedId
    private VersionId id;  // Composite key: major, minor, patch

    public static Version versionStart() {
        return new Version(new VersionId(0, 0, 1));
    }
}
```

### 2. Spring Data JPA Auto-implementation

- Define only repository interfaces
- Automatic JPA implementation generation
- Automated transaction management

### 3. Profile-based Environment Separation

- Development: H2 in-memory + web console
- Production: MySQL/PostgreSQL + production settings
- Environment variable-based security

### 4. Loose Coupling Between Modules

- Separation of compile-time and runtime dependencies
- Easy addition of new databases
- Separation of domain logic and infrastructure

## Extension Guide

### Adding New Database

1. Add driver dependency to `storage/rdb/build.gradle`
2. Create `application-{db}.yml` configuration file
3. Add `{Database}Configuration.java` if needed

### Adding New Entity

1. Create `core/domain/src/main/java/app/{domain}/` package
2. Write Entity, Repository, Service classes
3. Automatically included in `@EntityScan` scope

## Getting Started

```bash
# 1. Clone project
git clone [repository-url]

# 2. Build and run
./gradlew :api:bootRun

# 3. Test API
curl http://localhost:8081/api

# 4. Access H2 console
open http://localhost:8081/h2-console
```

This template provides a structure that follows the core principles of domain-driven design while enabling practical implementation.
