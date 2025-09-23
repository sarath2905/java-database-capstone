package com.project.back_end.controllers;

import com.project.back_end.models.Login;
import com.project.back_end.models.Patient;
import com.project.back_end.services.PatientService;
import com.project.back_end.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
    private final Service service;

    @Autowired
    public PatientController(PatientService patientService, Service service) {
        this.patientService = patientService;
        this.service = service;
    }

    /**
     * ✅ Get Patient Details
     */
    @GetMapping("/{token}")
    public ResponseEntity<?> getPatientDetails(@PathVariable String token) {
        ResponseEntity<Map<String, String>> validationResponse = service.validateToken(token, "patient");
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }
        return patientService.getPatientDetails(token);
    }

    /**
     * ✅ Create a New Patient (Signup)
     */
    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody Patient patient) {
        // Validate if patient already exists
        boolean valid = service.validatePatient(patient);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Patient with email id or phone no already exist"));
        }

        int result = patientService.createPatient(patient);
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Signup successful"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal server error"));
        }
    }

    /**
     * ✅ Patient Login
     */
    @PostMapping("/login")
    public ResponseEntity<?> patientLogin(@RequestBody Login login) {
        return service.validatePatientLogin(login);
    }

    /**
     * ✅ Get Patient Appointments
     */
    @GetMapping("/{id}/{token}")
    public ResponseEntity<?> getPatientAppointments(
            @PathVariable Long id,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> validationResponse = service.validateToken(token, "patient");
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        return patientService.getPatientAppointment(id, token);
    }

    /**
     * ✅ Filter Patient Appointments
     */
    @GetMapping("/filter/{condition}/{name}/{token}")
    public ResponseEntity<?> filterAppointments(
            @PathVariable String condition,
            @PathVariable String name,
            @PathVariable String token) {

        ResponseEntity<Map<String, String>> validationResponse = service.validateToken(token, "patient");
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        return service.filterPatient(condition, name, token);
    }
}
