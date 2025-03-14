package com.example.festivalcultura.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.festivalcultura.models.Utente;
import com.example.festivalcultura.services.UtenteService;

import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UtenteService utenteService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Utente utente) {
        utenteService.register(utente);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
