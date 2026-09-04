package com.portfolio.personal.config;

import com.portfolio.personal.domain.AdminUser;
import com.portfolio.personal.domain.Profile;
import com.portfolio.personal.domain.Qualification;
import com.portfolio.personal.domain.ServiceOffering;
import com.portfolio.personal.repository.AdminUserRepository;
import com.portfolio.personal.repository.ProfileRepository;
import com.portfolio.personal.repository.QualificationRepository;
import com.portfolio.personal.repository.ServiceOfferingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(
            AdminUserRepository adminUserRepository,
            ProfileRepository profileRepository,
            QualificationRepository qualificationRepository,
            ServiceOfferingRepository serviceOfferingRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.username:admin}") String adminUsername,
            @Value("${app.admin.password:changeMe123}") String adminPassword
    ) {
        return args -> {
            AdminUser admin = adminUserRepository.findByUsername(adminUsername).orElseGet(AdminUser::new);
            admin.setUsername(adminUsername);
            admin.setPasswordHash(passwordEncoder.encode(adminPassword));
            admin.setRole("ADMIN");
            adminUserRepository.save(admin);

            Profile profile = profileRepository.findAll().stream().findFirst().orElseGet(Profile::new);
            profile.setFirstName("Erges");
            profile.setLastName("Bruka");
            if (profile.getPhotoUrl() == null || profile.getPhotoUrl().isBlank()) {
                profile.setPhotoUrl("/images/profile-placeholder.svg");
            }
            if (profile.getShortBio() == null || profile.getShortBio().isBlank()
                    || profile.getShortBio().contains("Zhvillues dhe profesionist")) {
                profile.setShortBio("Student i inxhinierisë së telekomunikacionit.");
            }
            profile.setStudyProgram("Bachelor – Inxhinieri Telekomunikacioni");
            if (profile.getEmail() == null || profile.getEmail().isBlank()) {
                profile.setEmail("contact@example.com");
            }
            profileRepository.save(profile);

            if (qualificationRepository.count() == 0) {
                Qualification q1 = new Qualification();
                q1.setTitle("Bachelor");
                q1.setInstitution("Universiteti");
                q1.setYearObtained("në vazhdim");
                q1.setDescription("Studioj për Inxhinieri Telekomunikacioni.");
                q1.setSortOrder(1);
                qualificationRepository.save(q1);
            } else {
                qualificationRepository.findAllByOrderBySortOrderAscIdAsc().stream()
                        .filter(q -> "Bachelor".equalsIgnoreCase(q.getTitle()))
                        .findFirst()
                        .ifPresent(q -> {
                            q.setDescription("Studioj për Inxhinieri Telekomunikacioni.");
                            q.setYearObtained("në vazhdim");
                            if (q.getInstitution() == null || q.getInstitution().isBlank()
                                    || "Universiteti".equals(q.getInstitution())) {
                                q.setInstitution("Universiteti");
                            }
                            qualificationRepository.save(q);
                        });
            }

            if (serviceOfferingRepository.count() == 0) {
                ServiceOffering s1 = new ServiceOffering();
                s1.setTitle("Konsulencë");
                s1.setDescription("Këshillim profesional sipas nevojave tuaja.");
                s1.setIcon("consulting");
                s1.setSortOrder(1);
                serviceOfferingRepository.save(s1);

                ServiceOffering s2 = new ServiceOffering();
                s2.setTitle("Zhvillim web");
                s2.setDescription("Ndërtim dhe mirëmbajtje e faqeve web.");
                s2.setIcon("web");
                s2.setSortOrder(2);
                serviceOfferingRepository.save(s2);
            }
        };
    }
}
