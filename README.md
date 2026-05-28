# Task Manager API

Backend-приложение для управления задачами и комментариями.  
Проект построен на Spring Boot и использует JWT-аутентификацию, роли пользователей, CRUD для задач и комментариев, валидацию, email-отправку и Swagger/OpenAPI документацию.

## Возможности

- Регистрация и аутентификация пользователей
- JWT-based security
- Роли `USER` и `ADMIN`
- CRUD-операции для задач
- CRUD-операции для комментариев
- Валидация входных данных
- Отправка писем через SMTP
- Swagger/OpenAPI документация
- Пагинация для списка задач

## Технологии

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- JWT
- JavaMailSender
- Swagger
- Maven

## Требования

- JDK 17 или выше
- Maven 3.9+
- PostgreSQL
- Доступ к SMTP Gmail
- Git

## Конфигурация

Приложение использует `application.yml`

Пример конфигурации:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/taskManager
    username: postgres
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect

  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher

  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-16-digit-app-password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000

server:
  port: 8080

logging:
  level:
    root: INFO
    com.project.task.manager: DEBUG
    org.springframework.security: DEBUG
```

## Локальный запуск

### 1. Установить зависимости

Нужны:
- JDK 17
- Maven
- PostgreSQL

### 2. Создать базу данных

```sql
CREATE DATABASE taskManager;
```

### 3. Указать переменные окружения

Перед запуском задай:

- `DB_PASSWORD`
- `JWT_SECRET`

Пример для Linux/macOS:

```bash
export DB_PASSWORD=your_postgres_password
export JWT_SECRET=your_super_secret_key
```

Пример для Windows PowerShell:

```powershell
$env:DB_PASSWORD="your_postgres_password"
$env:JWT_SECRET="your_super_secret_key"
```

### 4. Настроить email

Для Gmail нужен **app password**, а не обычный пароль от аккаунта.  
В конфигурации укажи:
- `spring.mail.username`
- `spring.mail.password`

### 5. Запустить приложение

Через Maven:

```bash
mvn spring-boot:run
```

Или через сборку:

```bash
mvn clean package
java -jar target/task-manager-api.jar
```

## API

После запуска приложение доступно по адресу:

- `http://localhost:8080`

Swagger UI:

- `http://localhost:8080/swagger-ui/index.html`

## Основные эндпоинты

### Auth
- `POST /api/auth/register`
- `POST /api/auth/login`

### Tasks
- `GET /api/tasks`
- `GET /api/tasks/{id}`
- `POST /api/tasks`
- `PUT /api/tasks/{id}`
- `DELETE /api/tasks/{id}`

### Comments
- `GET /api/comments`
- `POST /api/comments`
- `PUT /api/comments/{id}`
- `DELETE /api/comments/{id}`

## Примеры запросов

### Регистрация

```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

### Создание задачи

```http
POST /api/tasks
Authorization: Bearer <your-token>
Content-Type: application/json

{
  "title": "Finish README",
  "description": "Write project documentation",
  "status": "OPEN"
}
```

## Тесты

Запуск тестов:

```bash
mvn test
```

## Структура проекта

- `src/main/java` — основной код приложения.
- `src/main/resources` — конфигурация и ресурсы.
- `src/test/java` — тесты.

## Дальнейшее развитие

- [ ] Добавить полноценную обработку ошибок.
- [ ] Улучшить покрытие тестами.
- [ ] Добавить email-верификацию и recovery flow.
- [ ] Улучшить архитектурное разделение слоёв.
- [ ] Добавить Docker.
- [ ] Добавить CI/CD.

## Вклад в проект

Если ты хочешь помочь проекту:

1. Сделай fork репозитория.
2. Создай отдельную ветку.
3. Внеси изменения.
4. Открой pull request.
