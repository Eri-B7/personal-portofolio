package com.portfolio.personal.web;

import com.portfolio.personal.service.PortfolioService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAdvice {

    private final PortfolioService portfolioService;

    public GlobalModelAdvice(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @ModelAttribute
    public void addProfile(Model model) {
        try {
            model.addAttribute("profile", portfolioService.getProfile());
        } catch (IllegalStateException ignored) {
            // Profile may not exist yet during early startup edge cases.
        }
    }
}
