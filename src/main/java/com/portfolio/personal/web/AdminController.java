package com.portfolio.personal.web;

import com.portfolio.personal.domain.Certification;
import com.portfolio.personal.domain.Profile;
import com.portfolio.personal.domain.Qualification;
import com.portfolio.personal.domain.ServiceOffering;
import com.portfolio.personal.service.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PortfolioService portfolioService;

    public AdminController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("profile", portfolioService.getProfile());
        model.addAttribute("serviceCount", portfolioService.listAllServices().size());
        model.addAttribute("qualificationCount", portfolioService.listQualifications().size());
        model.addAttribute("certificationCount", portfolioService.listCertifications().size());
        model.addAttribute("unreadMessages", portfolioService.countUnreadMessages());
        return "admin/dashboard";
    }

    @GetMapping("/profile")
    public String editProfile(Model model) {
        model.addAttribute("pageTitle", "Edit Profile");
        model.addAttribute("activePage", "profile");
        model.addAttribute("profile", portfolioService.getProfile());
        return "admin/profile";
    }

    @PostMapping("/profile")
    public String saveProfile(
            @Valid @ModelAttribute("profile") Profile profile,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Profile");
            model.addAttribute("activePage", "profile");
            return "admin/profile";
        }
        portfolioService.saveProfile(profile);
        redirectAttributes.addFlashAttribute("success", "Profili u përditësua. Ndryshimet duken menjëherë në faqen publike.");
        return "redirect:/admin/profile";
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("pageTitle", "Manage Services");
        model.addAttribute("activePage", "services");
        model.addAttribute("services", portfolioService.listAllServices());
        if (!model.containsAttribute("serviceForm")) {
            ServiceOffering blank = new ServiceOffering();
            blank.setActive(true);
            blank.setSortOrder(0);
            model.addAttribute("serviceForm", blank);
        }
        if (!model.containsAttribute("editing")) {
            model.addAttribute("editing", false);
        }
        return "admin/services";
    }

    @GetMapping("/services/{id}/edit")
    public String editService(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Service");
        model.addAttribute("activePage", "services");
        model.addAttribute("services", portfolioService.listAllServices());
        model.addAttribute("serviceForm", portfolioService.getService(id));
        model.addAttribute("editing", true);
        return "admin/services";
    }

    @PostMapping("/services")
    public String saveService(
            @ModelAttribute("serviceForm") ServiceOffering service,
            RedirectAttributes redirectAttributes
    ) {
        if (service.getSortOrder() == null) {
            service.setSortOrder(0);
        }
        boolean updating = service.getId() != null;
        portfolioService.saveService(service);
        redirectAttributes.addFlashAttribute(
                "success",
                updating ? "Shërbimi u përditësua." : "Shërbimi u shtua."
        );
        return "redirect:/admin/services";
    }

    @PostMapping("/services/{id}/delete")
    public String deleteService(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        portfolioService.deleteService(id);
        redirectAttributes.addFlashAttribute("success", "Shërbimi u fshi.");
        return "redirect:/admin/services";
    }

    @GetMapping("/qualifications")
    public String qualifications(Model model) {
        model.addAttribute("pageTitle", "Manage Qualifications");
        model.addAttribute("activePage", "qualifications");
        model.addAttribute("qualifications", portfolioService.listQualifications());
        if (!model.containsAttribute("qualificationForm")) {
            Qualification blank = new Qualification();
            blank.setSortOrder(0);
            model.addAttribute("qualificationForm", blank);
        }
        if (!model.containsAttribute("editing")) {
            model.addAttribute("editing", false);
        }
        return "admin/qualifications";
    }

    @GetMapping("/qualifications/{id}/edit")
    public String editQualification(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Qualification");
        model.addAttribute("activePage", "qualifications");
        model.addAttribute("qualifications", portfolioService.listQualifications());
        model.addAttribute("qualificationForm", portfolioService.getQualification(id));
        model.addAttribute("editing", true);
        return "admin/qualifications";
    }

    @PostMapping("/qualifications")
    public String saveQualification(
            @ModelAttribute("qualificationForm") Qualification qualification,
            RedirectAttributes redirectAttributes
    ) {
        if (qualification.getSortOrder() == null) {
            qualification.setSortOrder(0);
        }
        boolean updating = qualification.getId() != null;
        portfolioService.saveQualification(qualification);
        redirectAttributes.addFlashAttribute(
                "success",
                updating ? "Kualifikimi u përditësua." : "Kualifikimi u shtua."
        );
        return "redirect:/admin/qualifications";
    }

    @PostMapping("/qualifications/{id}/delete")
    public String deleteQualification(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        portfolioService.deleteQualification(id);
        redirectAttributes.addFlashAttribute("success", "Kualifikimi u fshi.");
        return "redirect:/admin/qualifications";
    }

    @GetMapping("/certifications")
    public String certifications(Model model) {
        model.addAttribute("pageTitle", "Manage Certifications");
        model.addAttribute("activePage", "certifications");
        model.addAttribute("certifications", portfolioService.listCertifications());
        if (!model.containsAttribute("certificationForm")) {
            Certification blank = new Certification();
            blank.setSortOrder(0);
            model.addAttribute("certificationForm", blank);
        }
        if (!model.containsAttribute("editing")) {
            model.addAttribute("editing", false);
        }
        return "admin/certifications";
    }

    @GetMapping("/certifications/{id}/edit")
    public String editCertification(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Certification");
        model.addAttribute("activePage", "certifications");
        model.addAttribute("certifications", portfolioService.listCertifications());
        model.addAttribute("certificationForm", portfolioService.getCertification(id));
        model.addAttribute("editing", true);
        return "admin/certifications";
    }

    @PostMapping("/certifications")
    public String saveCertification(
            @ModelAttribute("certificationForm") Certification certification,
            RedirectAttributes redirectAttributes
    ) {
        if (certification.getSortOrder() == null) {
            certification.setSortOrder(0);
        }
        boolean updating = certification.getId() != null;
        portfolioService.saveCertification(certification);
        redirectAttributes.addFlashAttribute(
                "success",
                updating ? "Certifikata u përditësua." : "Certifikata u shtua."
        );
        return "redirect:/admin/certifications";
    }

    @PostMapping("/certifications/{id}/delete")
    public String deleteCertification(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        portfolioService.deleteCertification(id);
        redirectAttributes.addFlashAttribute("success", "Certifikata u fshi.");
        return "redirect:/admin/certifications";
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        model.addAttribute("pageTitle", "Messages");
        model.addAttribute("activePage", "messages");
        model.addAttribute("messages", portfolioService.listMessages());
        return "admin/messages";
    }
}
