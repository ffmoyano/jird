package com.ffmoyano.jird.controller;

import com.ffmoyano.jird.entity.Link;
import com.ffmoyano.jird.service.LinkService;
import com.ffmoyano.jird.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/")
public class RedirectController {


    private final UserService userService;
    private final LinkService linkService;

    public RedirectController(UserService userService, LinkService linkService) {
        this.userService = userService;
        this.linkService = linkService;
    }

    @GetMapping({"/"})
    public String authRedirect() {
        if(userService.isUserAuthenticated()) {
            return "redirect:/link/";
        } else {
            return "redirect:/auth/login";
        }
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> redirect(@PathVariable(value = "shortUrl") String shortUrl) {
        Link link = linkService.findByShortUrl(shortUrl);
        if(link != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(link.getUrl()))
                    .build();
        } else {
            throw new ResponseStatusException(NOT_FOUND);
        }

    }

}
