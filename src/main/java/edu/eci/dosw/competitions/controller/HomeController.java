package edu.eci.dosw.competitions.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "service", "TechCup Competitions API",
                "status", "running",
                "docs", "/swagger-ui/index.html"
        );
    }
}