-- Schema reference for portfolio_db (MySQL 8+)
CREATE DATABASE IF NOT EXISTS portfolio_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE portfolio_db;

CREATE TABLE IF NOT EXISTS admin_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    display_name VARCHAR(120) NOT NULL,
    role VARCHAR(40) NOT NULL DEFAULT 'ROLE_ADMIN',
    created_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    slug VARCHAR(180) NOT NULL UNIQUE,
    short_description VARCHAR(500) NOT NULL,
    full_description TEXT NOT NULL,
    tech_stack VARCHAR(500) NOT NULL,
    highlights TEXT,
    duration_label VARCHAR(80),
    github_url VARCHAR(300),
    live_url VARCHAR(300),
    swagger_url VARCHAR(300),
    image_url VARCHAR(300),
    featured BOOLEAN NOT NULL DEFAULT TRUE,
    display_order INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS certificates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(180) NOT NULL,
    issuer VARCHAR(150) NOT NULL,
    issue_date VARCHAR(80),
    credential_id VARCHAR(150),
    credential_url VARCHAR(300),
    image_url VARCHAR(300) NOT NULL,
    pdf_url VARCHAR(300),
    description VARCHAR(600),
    display_order INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS contact_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(180) NOT NULL,
    subject VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'UNREAD',
    created_at DATETIME NOT NULL
);
