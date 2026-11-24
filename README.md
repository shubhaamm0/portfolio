# Shubham Kumar Rai — Java Backend Developer Portfolio

A full-stack, database-driven developer portfolio and admin CMS built with **Java 17**, **Spring Boot 3.4.5**, **Spring MVC**, **Spring Security**, **Spring Data JPA / Hibernate**, **Thymeleaf**, **Bootstrap 5**, and **MySQL**.

---

## Key Features

- **Dark-First Modern UI with Light/Dark Mode Toggle**: Responsive navy/black palette, subtle glassmorphism, purple/blue gradient accents, animated tech-grid hero canvas, and Lucide icons.
- **Authentic Resume-Backed Content**: Education timeline, verified technical skills, downloadable PDF resume (`/resume/download`), profile photo, and developer links.
- **Featured Projects & Case Studies**:
  - **FitPulse AI – AI-Powered Fitness & Workout Companion** (Live Demo + Swagger UI)
  - **URL Shortener Service** (GitHub repository)
  - **Employee Management System** (GitHub repository)
  - Individual SEO-friendly project detail pages (`/projects/{slug}`).
- **Certifications Gallery & Lightbox**: Real certificate previews rendered from original credentials with full-size lightbox inspection, dedicated detail views, and original PDF links.
- **Contact Form with Validation & MySQL Persistence**: Progressive AJAX + MVC form handling (`POST /api/contact` and `POST /contact`) with Jakarta Bean Validation and MySQL storage.
- **Secure Admin Dashboard (`/admin`)**: Protected with Spring Security and BCrypt password hashing. Supports full Project CRUD, Certificate image upload/CRUD, and Contact Message triage (mark as read / delete).
- **Custom Error Handling**: Global exception handler (`@ControllerAdvice`) and custom `404` / `500` error pages.

---

## Project Architecture

```text
com.portfolio
├── config          # WebMvcConfig, DataSeeder, PortfolioProfile
├── controller      # HomeController, ProjectController, CertificateController, ContactController, AdminController, CustomErrorController
├── dto             # ProjectDto, CertificateDto, ContactMessageDto, ApiResponse
├── entity          # Project, Certificate, ContactMessage, AdminUser
├── exception       # ResourceNotFoundException, FileStorageException, GlobalExceptionHandler
├── repository      # ProjectRepository, CertificateRepository, ContactMessageRepository, AdminUserRepository
├── security        # SecurityConfig, CustomUserDetailsService
├── service         # ProjectService, CertificateService, ContactService, FileStorageService
└── util            # SlugUtil
```

---

## Database Setup (MySQL)

1. Ensure MySQL 8+ is running locally on port `3306`.
2. Create the database (or let Hibernate `createDatabaseIfNotExist=true` create it automatically):

```sql
CREATE DATABASE IF NOT EXISTS portfolio_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

An explicit DDL reference is also included at `src/main/resources/schema-mysql.sql`.

---

## Environment Variables

Configure credentials using environment variables (see `.env.example`):

| Variable | Description | Default |
|---|---|---|
| `PORT` | HTTP server port | `8080` |
| `DB_URL` | JDBC URL for `portfolio_db` | `jdbc:mysql://localhost:3306/portfolio_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` |
| `DB_USERNAME` | MySQL username | `root` |
| `DB_PASSWORD` | MySQL password | `root` |
| `ADMIN_USERNAME` | Seeded admin login username | `admin` |
| `ADMIN_PASSWORD` | Seeded admin login password (hashed with BCrypt on seed) | `Admin@12345` |
| `UPLOAD_DIR` | Local directory for admin-uploaded images | `uploads` |

---

## Running the Application

Using Maven:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
$env:ADMIN_USERNAME="admin"
$env:ADMIN_PASSWORD="your_admin_password"
mvn spring-boot:run
```

Then open:
- Public Portfolio: `http://localhost:8080/`
- Admin Login: `http://localhost:8080/admin/login`

---

## Deploying Publicly (Render + Cloud MySQL)

This project includes a multi-stage [`Dockerfile`](Dockerfile), [`.dockerignore`](.dockerignore), and [`render.yaml`](render.yaml) ready for public deployment:

1. **Create a Cloud MySQL Database** (e.g., Railway MySQL, Aiven for MySQL, or TiDB Cloud) and note the host, port, database name (`portfolio_db`), username, and password.
2. **Push this project to GitHub**:
   ```powershell
   git init
   git add .
   git commit -m "Initial portfolio release"
   git branch -M main
   git remote add origin https://github.com/shubhaamm0/portfolio.git
   git push -u origin main
   ```
3. **Create a Web Service on Render**:
   - Connect your GitHub repository and select **Docker** as the runtime (Render will use `Dockerfile` / `render.yaml`).
   - Add the environment variables:
     - `PORT` = `8080`
     - `DB_URL` = `jdbc:mysql://<host>:<port>/portfolio_db?createDatabaseIfNotExist=true&useSSL=true&requireSSL=true&serverTimezone=UTC`
     - `DB_USERNAME` = `<your_db_user>`
     - `DB_PASSWORD` = `<your_db_password>`
     - `ADMIN_USERNAME` = `admin`
     - `ADMIN_PASSWORD` = `<your_strong_admin_password>`
     - `UPLOAD_DIR` = `/app/uploads`
4. Deploy and access your public `https://<your-service>.onrender.com` URL.
