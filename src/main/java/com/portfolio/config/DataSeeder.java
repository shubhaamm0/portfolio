package com.portfolio.config;

import com.portfolio.entity.AdminUser;
import com.portfolio.entity.Certificate;
import com.portfolio.entity.Project;
import com.portfolio.repository.AdminUserRepository;
import com.portfolio.repository.CertificateRepository;
import com.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final ProjectRepository projectRepository;
    private final CertificateRepository certificateRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:Admin@12345}")
    private String adminPassword;

    public DataSeeder(AdminUserRepository adminUserRepository,
                      ProjectRepository projectRepository,
                      CertificateRepository certificateRepository,
                      PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.projectRepository = projectRepository;
        this.certificateRepository = certificateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedAdminUser();
        seedProjects();
        seedCertificates();
    }

    private void seedAdminUser() {
        if (adminUserRepository.findByUsername(adminUsername).isEmpty()) {
            AdminUser admin = new AdminUser();
            admin.setUsername(adminUsername);
            admin.setPasswordHash(passwordEncoder.encode(adminPassword));
            admin.setDisplayName("Shubham Kumar Rai");
            admin.setRole("ROLE_ADMIN");
            adminUserRepository.save(admin);
        }
    }

    private void seedProjects() {
        if (projectRepository.count() > 0) {
            return;
        }

        Project fitPulse = new Project();
        fitPulse.setTitle("FitPulse AI – AI-Powered Fitness & Workout Companion");
        fitPulse.setSlug("fitpulse-ai-fitness-tracker");
        fitPulse.setShortDescription("Full-stack fitness tracking application with Spring Security JWT authentication, Gemini AI recommendations via Spring AI, MySQL persistence, and live Docker deployment on Render.");
        fitPulse.setFullDescription(
                "FitPulse AI is a production-deployed fitness and workout tracking platform built with Java 17 and Spring Boot 3.4.5. "
                + "It secures user registration and login using Spring Security with stateless JWT authentication, exposes documented REST APIs through Swagger/OpenAPI, "
                + "and integrates Google Gemini via Spring AI to generate personalized workout and fitness recommendations based on recorded activity data. "
                + "Data is persisted in MySQL with Spring Data JPA and Hibernate across clean Controller, Service, and Repository layers, containerized with Docker, and deployed live on Render."
        );
        fitPulse.setTechStack("Java 17, Spring Boot 3.4.5, Spring Security, JWT, Spring Data JPA, Hibernate, MySQL, Spring AI, Gemini API, Docker, Swagger/OpenAPI, Render");
        fitPulse.setHighlights(
                "Built a full-stack fitness tracking application with secure registration and login using Spring Security and JWT authentication.\n"
                + "Designed REST APIs for user profiles, fitness activities, and personalized recommendations, documented and tested with Swagger/OpenAPI.\n"
                + "Integrated Gemini through Spring AI to generate personalized workout and fitness recommendations from user activity data.\n"
                + "Persisted user and activity data in MySQL using Spring Data JPA and Hibernate.\n"
                + "Structured the backend in Controller, Service, and Repository layers for modularity and maintainability.\n"
                + "Containerized the app with Docker and deployed it live on Render, using environment variables for secrets and database configuration."
        );
        fitPulse.setDurationLabel("November 2026");
        fitPulse.setGithubUrl("https://github.com/shubhaamm0");
        fitPulse.setLiveUrl("https://fitness-tracker-ai-3.onrender.com");
        fitPulse.setSwaggerUrl("https://fitness-tracker-ai-3.onrender.com/swagger-ui/index.html");
        fitPulse.setImageUrl("/images/projects/fitpulse-ai.svg");
        fitPulse.setFeatured(true);
        fitPulse.setDisplayOrder(1);

        Project urlShortener = new Project();
        urlShortener.setTitle("URL Shortener Service");
        urlShortener.setSlug("url-shortener-service");
        urlShortener.setShortDescription("Backend URL shortening service built with Spring Boot REST APIs and MySQL, supporting unique short code generation, HTTP redirection, and click analytics.");
        urlShortener.setFullDescription(
                "A scalable URL Shortener REST service engineered with Java and Spring Boot. "
                + "The application generates unique compact short codes for long URLs, performs fast HTTP redirections, and tracks click metrics and usage analytics backed by MySQL and Spring Data JPA."
        );
        urlShortener.setTechStack("Java 17, Spring Boot, REST APIs, Spring Data JPA, Hibernate, MySQL, Maven");
        urlShortener.setHighlights(
                "Unique short URL generation with collision-safe mapping.\n"
                + "Fast URL redirection endpoints via Spring MVC REST controllers.\n"
                + "Click tracking and analytics persisted in MySQL.\n"
                + "Clean layered Spring Boot architecture separating controllers, services, and repositories."
        );
        urlShortener.setDurationLabel("2026");
        urlShortener.setGithubUrl("https://github.com/ShubhaamRai/URL-SHORTNER");
        urlShortener.setImageUrl("/images/projects/url-shortener.svg");
        urlShortener.setFeatured(true);
        urlShortener.setDisplayOrder(2);

        Project employeeMgmt = new Project();
        employeeMgmt.setTitle("Employee Management System");
        employeeMgmt.setSlug("employee-management-system");
        employeeMgmt.setShortDescription("Full-stack Spring Boot MVC and Thymeleaf application supporting 5 CRUD operations, One-to-Many department relationships, and profile image upload with live preview.");
        employeeMgmt.setFullDescription(
                "A full-stack Employee Management web application built with Java, Spring Boot, Spring MVC, Spring Data JPA, Hibernate, and Thymeleaf. "
                + "Designed with clean Controller, Service, and Repository separation, the system supports employee and department management via One-to-Many JPA relationships, "
                + "complete CRUD workflows, dynamic department creation, and employee profile image uploads with live client-side preview."
        );
        employeeMgmt.setTechStack("Java, Spring Boot, Spring MVC, Spring Data JPA, Hibernate, Thymeleaf, MySQL");
        employeeMgmt.setHighlights(
                "Built a full-stack web application with 5 CRUD operations and One-to-Many JPA relationships.\n"
                + "Implemented profile image upload with live preview and dynamic department creation.\n"
                + "Applied Spring Boot MVC architecture with clean separation of controller, service, and repository layers for maintainability and scalability."
        );
        employeeMgmt.setDurationLabel("February 2026 – March 2026");
        employeeMgmt.setGithubUrl("https://github.com/ShubhaamRai/EmployeeManagementSystem");
        employeeMgmt.setImageUrl("/images/projects/employee-management.svg");
        employeeMgmt.setFeatured(true);
        employeeMgmt.setDisplayOrder(3);

        projectRepository.saveAll(List.of(fitPulse, urlShortener, employeeMgmt));
    }

    private void seedCertificates() {
        if (certificateRepository.count() > 0) {
            return;
        }

        Certificate jpmorgan = new Certificate();
        jpmorgan.setTitle("Software Engineering Job Simulation");
        jpmorgan.setIssuer("JPMorgan Chase & Co. (Forage)");
        jpmorgan.setIssueDate("March 30, 2026");
        jpmorgan.setCredentialId("D2BJZcKFgY5E57eFa | User Code: fAoY6rJDpykavL2ao");
        jpmorgan.setImageUrl("/images/certificates/jpmorgan-forage.png");
        jpmorgan.setPdfUrl("/images/certificates/jpmorgan-forage.pdf");
        jpmorgan.setDescription("Completed practical tasks in Project Setup, Kafka Integration, H2 Integration, REST API Integration, and REST API Controller.");
        jpmorgan.setDisplayOrder(1);

        Certificate sqlCert = new Certificate();
        sqlCert.setTitle("Introduction to SQL");
        sqlCert.setIssuer("Simplilearn SkillUP");
        sqlCert.setIssueDate("March 30, 2026");
        sqlCert.setCredentialId("10030850");
        sqlCert.setImageUrl("/images/certificates/introduction-to-sql.png");
        sqlCert.setPdfUrl("/images/certificates/introduction-to-sql.pdf");
        sqlCert.setDescription("Completed online course covering relational database fundamentals and SQL querying.");
        sqlCert.setDisplayOrder(2);

        Certificate udemyJava = new Certificate();
        udemyJava.setTitle("Java Tutorial for Complete Beginners");
        udemyJava.setIssuer("Udemy (Instructor: John Purcell)");
        udemyJava.setIssueDate("March 30, 2026");
        udemyJava.setCredentialId("UC-f91f4335-dc9c-446e-bbde-1167aca0418b");
        udemyJava.setCredentialUrl("https://ude.my/UC-f91f4335-dc9c-446e-bbde-1167aca0418b");
        udemyJava.setImageUrl("/images/certificates/java-udemy.png");
        udemyJava.setPdfUrl("/images/certificates/java-udemy.pdf");
        udemyJava.setDescription("16 total hours of Core Java programming fundamentals, object-oriented principles, collections, and exception handling.");
        udemyJava.setDisplayOrder(3);

        certificateRepository.saveAll(List.of(jpmorgan, sqlCert, udemyJava));
    }
}
