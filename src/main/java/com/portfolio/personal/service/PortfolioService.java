package com.portfolio.personal.service;

import com.portfolio.personal.domain.Certification;
import com.portfolio.personal.domain.ContactMessage;
import com.portfolio.personal.domain.Profile;
import com.portfolio.personal.domain.Qualification;
import com.portfolio.personal.domain.ServiceOffering;
import com.portfolio.personal.repository.CertificationRepository;
import com.portfolio.personal.repository.ContactMessageRepository;
import com.portfolio.personal.repository.ProfileRepository;
import com.portfolio.personal.repository.QualificationRepository;
import com.portfolio.personal.repository.ServiceOfferingRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PortfolioService {

    private final ProfileRepository profileRepository;
    private final QualificationRepository qualificationRepository;
    private final CertificationRepository certificationRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final ContactMessageRepository contactMessageRepository;

    public PortfolioService(
            ProfileRepository profileRepository,
            QualificationRepository qualificationRepository,
            CertificationRepository certificationRepository,
            ServiceOfferingRepository serviceOfferingRepository,
            ContactMessageRepository contactMessageRepository
    ) {
        this.profileRepository = profileRepository;
        this.qualificationRepository = qualificationRepository;
        this.certificationRepository = certificationRepository;
        this.serviceOfferingRepository = serviceOfferingRepository;
        this.contactMessageRepository = contactMessageRepository;
    }

    public Profile getProfile() {
        return profileRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Profile not initialized"));
    }

    @Transactional
    public Profile saveProfile(Profile profile) {
        Profile existing = getProfile();
        existing.setFirstName(profile.getFirstName());
        existing.setLastName(profile.getLastName());
        existing.setPhotoUrl(profile.getPhotoUrl());
        existing.setShortBio(profile.getShortBio());
        existing.setStudyProgram(profile.getStudyProgram());
        existing.setEmail(profile.getEmail());
        return profileRepository.save(existing);
    }

    public List<Qualification> listQualifications() {
        return qualificationRepository.findAllByOrderBySortOrderAscIdAsc();
    }

    public Qualification getQualification(Long id) {
        return qualificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Qualification not found"));
    }

    @Transactional
    public Qualification saveQualification(Qualification qualification) {
        return qualificationRepository.save(qualification);
    }

    @Transactional
    public void deleteQualification(Long id) {
        qualificationRepository.deleteById(id);
    }

    public List<Certification> listCertifications() {
        return certificationRepository.findAllByOrderBySortOrderAscIdAsc();
    }

    public Certification getCertification(Long id) {
        return certificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Certification not found"));
    }

    @Transactional
    public Certification saveCertification(Certification certification) {
        return certificationRepository.save(certification);
    }

    @Transactional
    public void deleteCertification(Long id) {
        certificationRepository.deleteById(id);
    }

    public List<ServiceOffering> listActiveServices() {
        return serviceOfferingRepository.findByActiveTrueOrderBySortOrderAscIdAsc();
    }

    public List<ServiceOffering> listAllServices() {
        return serviceOfferingRepository.findAllByOrderBySortOrderAscIdAsc();
    }

    public ServiceOffering getService(Long id) {
        return serviceOfferingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));
    }

    @Transactional
    public ServiceOffering saveService(ServiceOffering service) {
        return serviceOfferingRepository.save(service);
    }

    @Transactional
    public void deleteService(Long id) {
        serviceOfferingRepository.deleteById(id);
    }

    @Transactional
    public ContactMessage saveContactMessage(ContactMessage message) {
        return contactMessageRepository.save(message);
    }

    public List<ContactMessage> listMessages() {
        return contactMessageRepository.findAllByOrderByCreatedAtDesc();
    }

    public long countUnreadMessages() {
        return contactMessageRepository.countByReadFlagFalse();
    }
}
