package com.portfolio.personal.web;

import com.portfolio.personal.domain.ContactMessage;
import com.portfolio.personal.service.PortfolioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PublicController {

    private final PortfolioService portfolioService;

    public PublicController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Home");
        model.addAttribute("activePage", "home");
        return "public/home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About");
        model.addAttribute("activePage", "about");
        model.addAttribute("qualifications", portfolioService.listQualifications());
        model.addAttribute("certifications", portfolioService.listCertifications());
        return "public/about";
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("pageTitle", "Services");
        model.addAttribute("activePage", "services");
        model.addAttribute("services", portfolioService.listActiveServices());
        return "public/services";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("pageTitle", "Contact");
        model.addAttribute("activePage", "contact");
        if (!model.containsAttribute("contactForm")) {
            model.addAttribute("contactForm", new ContactForm());
        }
        return "public/contact";
    }

    @PostMapping("/contact")
    public String submitContact(
            @Valid @ModelAttribute("contactForm") ContactForm contactForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Contact");
            model.addAttribute("activePage", "contact");
            return "public/contact";
        }

        ContactMessage message = new ContactMessage();
        message.setName(contactForm.getName());
        message.setEmail(contactForm.getEmail());
        message.setSubject(contactForm.getSubject());
        message.setMessage(contactForm.getMessage());
        portfolioService.saveContactMessage(message);

        redirectAttributes.addFlashAttribute("success", "Mesazhi u dërgua me sukses.");
        return "redirect:/contact";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("pageTitle", "Admin Login");
        model.addAttribute("activePage", "login");
        return "admin/login";
    }

    public static class ContactForm {

        @NotBlank
        @Size(max = 150)
        private String name;

        @NotBlank
        @Email
        @Size(max = 200)
        private String email;

        @Size(max = 200)
        private String subject;

        @NotBlank
        @Size(max = 4000)
        private String message;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
