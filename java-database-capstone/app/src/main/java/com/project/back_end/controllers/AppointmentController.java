package com.project.back_end.controllers;

import com.project.back_end.models.Appointment;
import com.project.back_end.services.AppointmentService;
import com.project.back_end.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final Service service;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, Service service) {
        this.appointmentService = appointmentService;
        this.service = service;
    }

    /**
     * ✅ GET Appointments for a Doctor (filtered by date & patient name)
     * Example: GET /appointments/2025-09-20/JohnDoe/{token}
     */
    @GetMapping("/{date}/{patientName}/{token}")
    public ResponseEntity<?> getAppointments(
            @PathVariable String date,
            @PathVariable String patientName,
            @PathVariable String token) {

        // Validate doctor token
        ResponseEntity<Map<String, String>> validationResponse = service.validateToken(token, "doctor");
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        // Convert date string
        LocalDate appointmentDate = LocalDate.parse(date);
        return ResponseEntity.ok(appointmentService.getAppointment(patientName, appointmentDate, token));
    }

    /**
     * ✅ POST: Book a new Appointment
     * Example: POST /appointments/{token}
     */
    @PostMapping("/{token}")
    public ResponseEntity<?> bookAppointment(
            @PathVariable String token,
            @RequestBody Appointment appointment) {

        // Validate patient token
        ResponseEntity<Map<String, String>> validationResponse = service.validateToken(token, "patient");
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        // Validate appointment time availability
        int validationResult = service.validateAppointment(appointment);
        if (validationResult == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Doctor not found"));
        } else if (validationResult == 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Appointment time unavailable"));
        }

        // Book appointment
        int result = appointmentService.bookAppointment(appointment);
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Appointment booked successfully"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to book appointment"));
    }

    /**
     * ✅ PUT: Update Appointment
     * Example: PUT /appointments/{token}
     */
    @PutMapping("/{token}")
    public ResponseEntity<?> updateAppointment(
            @PathVariable String token,
            @RequestBody Appointment appointment) {

        // Validate patient token
        ResponseEntity<Map<String, String>> validationResponse = service.validateToken(token, "patient");
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        return appointmentService.updateAppointment(appointment);
    }

    /**
     * ✅ DELETE: Cancel Appointment
     * Example: DELETE /appointments/{id}/{token}
     */
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<?> cancelAppointment(
            @PathVariable long id,
            @PathVariable String token) {

        // Validate patient token
        ResponseEntity<Map<String, String>> validationResponse = service.validateToken(token, "patient");
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        return appointmentService.cancelAppointment(id, token);
    }
}
