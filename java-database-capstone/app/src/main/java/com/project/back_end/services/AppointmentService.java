package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.repositories.AppointmentRepository;
import com.project.back_end.repositories.DoctorRepository;
import com.project.back_end.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service class for managing appointments.
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TokenService tokenService;

    /**
     * Book a new appointment.
     *
     * @param appointment Appointment object
     * @return 1 if successful, 0 otherwise
     */
    public int bookAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception e) {
            return 0; // booking failed
        }
    }

    /**
     * Update an existing appointment.
     *
     * @param appointment Appointment object with updated data
     * @return ResponseEntity with success/failure message
     */
    public ResponseEntity<Map<String, String>> updateAppointment(Appointment appointment) {
        Map<String, String> response = new HashMap<>();

        return appointmentRepository.findById(appointment.getId())
                .map(existing -> {
                    // Optional: validate appointment with custom validation method
                    // if (!validateAppointment(appointment)) {
                    //     response.put("message", "Invalid appointment data");
                    //     return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                    // }

                    appointmentRepository.save(appointment);
                    response.put("message", "Appointment updated successfully");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    response.put("message", "Appointment not found");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                });
    }

    /**
     * Cancel an existing appointment.
     *
     * @param id    Appointment ID
     * @param token Authorization token
     * @return ResponseEntity with success/failure message
     */
    public ResponseEntity<Map<String, String>> cancelAppointment(long id, String token) {
        Map<String, String> response = new HashMap<>();

        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

        if (optionalAppointment.isEmpty()) {
            response.put("message", "Appointment not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Appointment appointment = optionalAppointment.get();

        Long patientIdFromToken = tokenService.extractUserId(token);

        if (!appointment.getPatient().getId().equals(patientIdFromToken)) {
            response.put("message", "Unauthorized cancellation attempt");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        appointmentRepository.delete(appointment);
        response.put("message", "Appointment cancelled successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get appointments for a doctor on a specific date, optionally filtered by patient name.
     *
     * @param pname Patient name filter (optional)
     * @param date  Appointment date
     * @param token Authorization token
     * @return Map containing the list of appointments
     */
    public Map<String, Object> getAppointment(String pname, LocalDate date, String token) {
        Map<String, Object> response = new HashMap<>();

        Long doctorId = tokenService.extractUserId(token);

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        List<Appointment> appointments;

        if (pname != null && !pname.isEmpty()) {
            appointments = appointmentRepository
                    .findByDoctorIdAndPatient_NameContainingIgnoreCaseAndAppointmentTimeBetween(
                            doctorId, pname, start, end
                    );
        } else {
            appointments = appointmentRepository
                    .findByDoctorIdAndAppointmentTimeBetween(doctorId, start, end);
        }

        response.put("appointments", appointments);
        return response;
    }
}
