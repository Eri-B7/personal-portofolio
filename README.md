# Personal Portfolio

Website personal me **Java 21 + Spring Boot 3**, **Thymeleaf**, **Spring Security** dhe **PostgreSQL** (H2 për development lokal).

## Çfarë përfshihet

- Faqe publike: Home, About, Services, Contact
- Panel admin (`/admin`): profil, shërbime, kualifikime, mesazhe kontakti
- Login vetëm për admin (`/login`)
- Vizitorët shohin përmbajtjen, nuk e ndryshojnë

## Kërkesat

- JDK 21+
- Maven Wrapper (`mvnw`) — nuk duhet Maven i instaluar
- Opsionale: Docker për PostgreSQL

## Nisja e shpejtë (H2, pa PostgreSQL)

```powershell
cd C:\Users\User\Projects\personal-portfolio
.\mvnw.cmd spring-boot:run
```

Hap: [http://localhost:8080](http://localhost:8080)

**Admin default**
- Username: `admin`
- Password: `changeMe123`

Ndryshoji me env vars: `ADMIN_USERNAME`, `ADMIN_PASSWORD`.

## PostgreSQL

```powershell
docker compose up -d
$env:SPRING_PROFILES_ACTIVE="postgres"
.\mvnw.cmd spring-boot:run
```

Kredencialet default të DB (shih `docker-compose.yml` / `application.yml`):
- DB: `portfolio`
- User/pass: `portfolio` / `portfolio`

## Domain & hosting

Domain-i vendoset më vonë. Për VPS/cloud: build JAR me `.\mvnw.cmd -DskipTests package` dhe ekzekuto me profilin `postgres` + HTTPS te reverse proxy (Nginx/Caddy).
