package com.ffmoyano.jird.controller;

import com.ffmoyano.jird.service.VerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;


@Controller
public class AuthController {

    private final VerificationService verificationService;

    public AuthController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }



    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> redirect(@PathVariable(value = "shortUrl") String shortUrl) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://www.google.es"))
                .build();
    }

    @GetMapping({"", "/login"})
    public String login(Model model) {
        if(verificationService.isAuthenticated()) {
            return user();
        } else {
            return "login";
        }

    }

    @GetMapping({ "/user/"})
    public String user() {
        return "user";
    }

    @GetMapping({ "/logout"})
    public String logout() {
        return "logout";
    }
}