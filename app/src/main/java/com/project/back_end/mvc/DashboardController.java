package com.project.back_end.controller;

import com.project.back_end.service.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private TokenValidationService tokenValidationService;

    @GetMapping("/adminDashboard/{token}")
    public String adminDashboard(@PathVariable String token) {
        Map<String, String> errors = tokenValidationService.validateToken(token, "admin");
        if (errors.isEmpty()) {
            return "admin/adminDashboard"; // maps to src/main/resources/templates/admin/adminDashboard.html
        } else {
            return "redirect:/"; // redirect to login
        }
    }

    @GetMapping("/doctorDashboard/{token}")
    public String doctorDashboard(@PathVariable String token) {
        Map<String, String> errors = tokenValidationService.validateToken(token, "doctor");
        if (errors.isEmpty()) {
            return "doctor/doctorDashboard"; // maps to src/main/resources/templates/doctor/doctorDashboard.html
        } else {
            return "redirect:/"; // redirect to login
        }
    }
}
