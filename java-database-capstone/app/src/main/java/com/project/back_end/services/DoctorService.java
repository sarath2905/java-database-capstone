package com.project.back_end.services;

import com.project.back_end.dto.Login;
import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.AppointmentRepository;
import com.project.back_end.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TokenService tokenService;

    // ✅ Get doctor availability by filtering out booked slots
    public List<String> getDoctorAvailability(Long doctorId, LocalDate date) {
        List<String> availableSlots = Arrays.asList("09:00-10:00", "10:00-11:00", "11:00-12:00",
                "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00");

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        List<Appointment> bookedAppointments =
                appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, start, end);

        Set<String> bookedSlots = bookedAppointments.stream()
                .map(a -> a.getAppointmentTime().toLocalTime().toString().substring(0, 5) + "-" +
                          a.getAppointmentTime().plusHours(1).toLocalTime().toString().substring(0, 5))
                .collect(Collectors.toSet());

        return availableSlots.stream()
                .filter(slot -> !bookedSlots.contains(slot))
                .collect(Collectors.toList());
    }

    // ✅ Save a new doctor
    public int saveDoctor(Doctor doctor) {
        try {
            if (doctorRepository.findByEmail(doctor.getEmail()) != null) {
                return -1; // doctor already exists
            }
            doctorRepository.save(doctor);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // ✅ Update doctor details
    public int updateDoctor(Doctor doctor) {
        try {
            Optional<Doctor> existing = doctorRepository.findById(doctor.getId());
            if (existing.isEmpty()) return -1;

            doctorRepository.save(doctor);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // ✅ Get all doctors
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    // ✅ Delete doctor (with appointments)
    public int deleteDoctor(long id) {
        try {
            Optional<Doctor> existing = doctorRepository.findById(id);
            if (existing.isEmpty()) return -1;

            appointmentRepository.deleteAllByDoctorId(id);
            doctorRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // ✅ Validate doctor login
    public ResponseEntity<Map<String, String>> validateDoctor(Login login) {
        Map<String, String> response = new HashMap<>();

        Doctor doctor = doctorRepository.findByEmail(login.getIdentifier());
        if (doctor == null || !doctor.getPassword().equals(login.getPassword())) {
            response.put("message", "Invalid email or password");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String token = tokenService.generateToken(doctor.getId(), "DOCTOR");
        response.put("token", token);
        response.put("message", "Login successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ✅ Find doctor by name (partial match)
    public Map<String, Object> findDoctorByName(String name) {
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctorRepository.findByNameLike("%" + name + "%"));
        return response;
    }

    // ✅ Filter doctors by name, specialty & time
    public Map<String, Object> filterDoctorsByNameSpecilityandTime(String name, String specialty, String amOrPm) {
        List<Doctor> doctors =
                doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);
        doctors = filterDoctorByTime(doctors, amOrPm);

        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctors);
        return response;
    }

    // ✅ Filter doctors by name & time
    public Map<String, Object> filterDoctorByNameAndTime(String name, String amOrPm) {
        List<Doctor> doctors = doctorRepository.findByNameLike("%" + name + "%");
        doctors = filterDoctorByTime(doctors, amOrPm);

        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctors);
        return response;
    }

    // ✅ Filter doctors by name & specialty
    public Map<String, Object> filterDoctorByNameAndSpecility(String name, String specialty) {
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctorRepository
                .findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty));
        return response;
    }

    // ✅ Filter doctors by specialty & time
    public Map<String, Object> filterDoctorByTimeAndSpecility(String specialty, String amOrPm) {
        List<Doctor> doctors = doctorRepository.findBySpecialtyIgnoreCase(specialty);
        doctors = filterDoctorByTime(doctors, amOrPm);

        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctors);
        return response;
    }

    // ✅ Filter doctors by specialty
    public Map<String, Object> filterDoctorBySpecility(String specialty) {
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctorRepository.findBySpecialtyIgnoreCase(specialty));
        return response;
    }

    // ✅ Filter doctors by time only
    public Map<String, Object> filterDoctorsByTime(String amOrPm) {
        List<Doctor> doctors = doctorRepository.findAll();
        doctors = filterDoctorByTime(doctors, amOrPm);

        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctors);
        return response;
    }

    // 🔒 Private helper to filter doctors by AM/PM slots
    private List<Doctor> filterDoctorByTime(List<Doctor> doctors, String amOrPm) {
        return doctors.stream()
                .filter(doc -> doc.getAvailableTimes().stream().anyMatch(time -> {
                    if (amOrPm.equalsIgnoreCase("AM")) {
                        return time.startsWith("09") || time.startsWith("10") || time.startsWith("11");
                    } else {
                        return time.startsWith("12") || time.startsWith("13") || time.startsWith("14")
                                || time.startsWith("15") || time.startsWith("16");
                    }
                }))
                .collect(Collectors.toList());
    }
}
