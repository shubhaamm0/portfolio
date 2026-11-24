package com.portfolio.service;

import com.portfolio.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS = Set.of("png", "jpg", "jpeg", "webp");

    private final Path uploadRoot;

    public FileStorageService(@Value("${app.upload.dir:uploads}") String uploadDir) {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadRoot);
        } catch (IOException ex) {
            throw new FileStorageException("Could not initialize upload directory", ex);
        }
    }

    public String storeImage(MultipartFile file, String subDirectory) {
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("Uploaded image file is empty");
        }

        String originalName = file.getOriginalFilename();
        String extension = extractExtension(originalName);
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
            throw new FileStorageException("Unsupported image format. Allowed formats: PNG, JPG, JPEG, WEBP.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.toLowerCase().startsWith("image/")) {
            throw new FileStorageException("Invalid file content type. Only image files are allowed.");
        }

        String cleanSubDir = subDirectory.replaceAll("[^a-zA-Z0-9_-]", "");
        Path targetDir = this.uploadRoot.resolve(cleanSubDir).normalize();
        if (!targetDir.startsWith(this.uploadRoot)) {
            throw new FileStorageException("Invalid upload target path");
        }

        try {
            Files.createDirectories(targetDir);
            String filename = UUID.randomUUID() + "." + extension;
            Path targetPath = targetDir.resolve(filename).normalize();
            if (!targetPath.startsWith(targetDir)) {
                throw new FileStorageException("Invalid target filename");
            }
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + cleanSubDir + "/" + filename;
        } catch (IOException ex) {
            throw new FileStorageException("Failed to store uploaded file", ex);
        }
    }

    public void deleteStoredFileIfLocalUpload(String urlPath) {
        if (urlPath == null || !urlPath.startsWith("/uploads/")) {
            return;
        }
        String relative = urlPath.substring("/uploads/".length());
        Path target = this.uploadRoot.resolve(relative).normalize();
        if (target.startsWith(this.uploadRoot)) {
            try {
                Files.deleteIfExists(target);
            } catch (IOException ignored) {
                // Non-critical cleanup failure
            }
        }
    }

    public Path getUploadRoot() {
        return uploadRoot;
    }

    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
}
