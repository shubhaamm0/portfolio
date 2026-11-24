package com.portfolio.config;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("portfolioProfile")
public class PortfolioProfile {

    public record SkillCategory(String title, String icon, List<String> items) {}

    public record EducationItem(
            String institution,
            String degree,
            String branch,
            String duration,
            String score,
            String location,
            boolean current
    ) {}

    public record ProfileLink(String label, String handle, String url, String icon, String description) {}

    public String getFullName() {
        return "Shubham Kumar Rai";
    }

    public String getHeadline() {
        return "Java Backend Developer | Spring Boot Developer";
    }

    public String getLocation() {
        return "Kolkata, India";
    }

    public String getEmail() {
        return "raishubhamkumar461@gmail.com";
    }

    public String getPhone() {
        return "+91-9572946976";
    }

    public String getGithubUrl() {
        return "https://github.com/shubhaamm0";
    }

    public String getGithubHandle() {
        return "github.com/shubhaamm0";
    }

    public String getLinkedinUrl() {
        return "https://www.linkedin.com/in/shubham-kumar-rai-1043a1252";
    }

    public String getLinkedinHandle() {
        return "linkedin.com/in/shubham-kumar-rai-1043a1252";
    }

    public String getProfileImageUrl() {
        return "/images/profile/shubham-rai.jpg";
    }

    public String getHeroSummary() {
        return "B.Tech Computer Science student and entry-level Java Developer building Spring Boot applications, RESTful APIs, and database-driven backends with Hibernate, Spring Data JPA, Spring Security, and Docker.";
    }

    public List<String> getAboutParagraphs() {
        return List.of(
                "I am a B.Tech Computer Science and Engineering (CSE) undergraduate at Brainware University, Kolkata (Expected 2027), focused on designing reliable backend systems using Java 17 and the Spring ecosystem.",
                "My hands-on experience spans Spring Boot, Spring MVC, Spring Data JPA, Hibernate, JDBC, and Spring Security with JWT authentication. I enjoy structuring applications cleanly across Controller, Service, and Repository layers, writing maintainable SQL and JPA queries against MySQL and PostgreSQL, and documenting endpoints with Swagger/OpenAPI.",
                "From integrating Gemini AI via Spring AI and containerizing FitPulse AI with Docker for Render deployment to completing the JPMorgan Chase & Co. Software Engineering Job Simulation, I am driven to solve backend engineering problems with strong OOP, data structures, and clean architectural practices."
        );
    }

    public List<SkillCategory> getSkillCategories() {
        return List.of(
                new SkillCategory(
                        "Programming Languages",
                        "code-2",
                        List.of("Java 17", "Core Java", "Collections", "Exception Handling", "SQL", "HTML5")
                ),
                new SkillCategory(
                        "Backend Development",
                        "server",
                        List.of("Spring Boot", "Spring MVC", "Spring Security", "Spring Data JPA", "Spring AI", "Hibernate", "JDBC", "REST APIs", "JWT Authentication", "Thymeleaf")
                ),
                new SkillCategory(
                        "Databases",
                        "database",
                        List.of("MySQL", "PostgreSQL", "H2 Database")
                ),
                new SkillCategory(
                        "Tools & Deployment",
                        "wrench",
                        List.of("Git", "GitHub", "IntelliJ IDEA", "Maven", "Postman", "Docker", "Swagger / OpenAPI", "Render")
                )
        );
    }

    public List<String> getCoreConcepts() {
        return List.of("Object-Oriented Programming (OOP)", "Data Structures", "MVC Architecture", "Layered Architecture", "Debugging", "Gemini API Integration");
    }

    public List<EducationItem> getEducationTimeline() {
        return List.of(
                new EducationItem(
                        "Brainware University, Kolkata",
                        "Bachelor of Technology (B.Tech)",
                        "Computer Science and Engineering (CSE)",
                        "Expected 2027",
                        "Pursuing Undergraduate Degree",
                        "Kolkata, India",
                        true
                ),
                new EducationItem(
                        "Central Board of Secondary Education (CBSE)",
                        "Senior Secondary (Class XII)",
                        "Science / Higher Secondary",
                        "2023",
                        "59.6%",
                        "India",
                        false
                ),
                new EducationItem(
                        "Central Board of Secondary Education (CBSE)",
                        "Secondary School (Class X)",
                        "Matriculation",
                        "2021",
                        "72%",
                        "India",
                        false
                )
        );
    }

    public List<ProfileLink> getVerifiedProfiles() {
        return List.of(
                new ProfileLink(
                        "GitHub",
                        "shubhaamm0",
                        "https://github.com/shubhaamm0",
                        "github",
                        "Source code repositories, Spring Boot services, and backend architecture projects."
                ),
                new ProfileLink(
                        "LinkedIn",
                        "shubham-kumar-rai-1043a1252",
                        "https://www.linkedin.com/in/shubham-kumar-rai-1043a1252",
                        "linkedin",
                        "Professional profile, academic background, and software engineering updates."
                )
        );
    }
}
