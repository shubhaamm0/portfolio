package com.portfolio;

import com.portfolio.entity.Certificate;
import com.portfolio.entity.ContactMessage;
import com.portfolio.entity.Project;
import com.portfolio.repository.CertificateRepository;
import com.portfolio.repository.ContactMessageRepository;
import com.portfolio.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PortfolioApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Test
    void publicPagesLoadSuccessfully() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Shubham Kumar Rai")))
                .andExpect(content().string(containsString("FitPulse AI")))
                .andExpect(content().string(containsString("Brainware University, Kolkata")));

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("URL Shortener Service")))
                .andExpect(content().string(containsString("Employee Management System")));

        mockMvc.perform(get("/projects/fitpulse-ai-fitness-tracker"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("https://fitness-tracker-ai-3.onrender.com")));

        mockMvc.perform(get("/certificates"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("JPMorgan Chase")));

        mockMvc.perform(get("/resume/download"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }

    @Test
    void unknownRouteReturns404() throws Exception {
        mockMvc.perform(get("/projects/does-not-exist-999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("404")));
    }

    @Test
    void contactFormAndApiValidateAndPersist() throws Exception {
        long before = contactMessageRepository.count();

        mockMvc.perform(post("/api/contact")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"A\",\"email\":\"bad\",\"subject\":\"Hi\",\"message\":\"short\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/contact")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Recruiter Test\",\"email\":\"recruiter@example.com\",\"subject\":\"Java Role\",\"message\":\"We would like to schedule an interview for Java Developer.\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));

        assertEquals(before + 1, contactMessageRepository.count());

        ContactMessage latest = contactMessageRepository.findAllByOrderByCreatedAtDesc().get(0);
        contactMessageRepository.deleteById(latest.getId());
    }

    @Test
    void adminRoutesRequireAuthentication() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/admin/login"));

        mockMvc.perform(get("/admin/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Admin Sign In")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminCrudProjectsCertificatesAndMessages() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Portfolio CMS Dashboard")));

        // Project Create -> Update -> Delete
        mockMvc.perform(post("/admin/projects")
                        .with(csrf())
                        .param("title", "Test Microservice")
                        .param("shortDescription", "Spring Boot test service")
                        .param("fullDescription", "Detailed description for test microservice.")
                        .param("techStack", "Java 17, Spring Boot")
                        .param("featured", "true")
                        .param("displayOrder", "9"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin#projects-panel"));

        Project savedProject = projectRepository.findBySlug("test-microservice").orElse(null);
        assertNotNull(savedProject);

        mockMvc.perform(post("/admin/projects/" + savedProject.getId())
                        .with(csrf())
                        .param("title", "Test Microservice Updated")
                        .param("shortDescription", "Updated short desc")
                        .param("fullDescription", "Updated detailed description.")
                        .param("techStack", "Java 17, Spring Boot, MySQL")
                        .param("featured", "true")
                        .param("displayOrder", "9"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/admin/projects/" + savedProject.getId() + "/delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertFalse(projectRepository.findById(savedProject.getId()).isPresent());

        // Certificate Create with image upload -> Delete
        MockMultipartFile certImg = new MockMultipartFile(
                "imageFile", "test-cert.png", "image/png", new byte[]{(byte) 137, 80, 78, 71});

        mockMvc.perform(multipart("/admin/certificates")
                        .file(certImg)
                        .with(csrf())
                        .param("title", "Spring Test Cert")
                        .param("issuer", "Spring Academy")
                        .param("issueDate", "2026")
                        .param("displayOrder", "9"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin#certificates-panel"));

        Certificate savedCert = certificateRepository.findAllByOrderByDisplayOrderAscCreatedAtDesc()
                .stream().filter(c -> "Spring Test Cert".equals(c.getTitle())).findFirst().orElse(null);
        assertNotNull(savedCert);
        assertTrue(savedCert.getImageUrl().startsWith("/uploads/certificates/"));

        mockMvc.perform(post("/admin/certificates/" + savedCert.getId() + "/delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
        assertFalse(certificateRepository.findById(savedCert.getId()).isPresent());
    }
}
