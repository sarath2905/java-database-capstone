package com.project.back_end.services;

import com.project.back_end.models.Admin;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.repositories.AdminRepository;
import com.project.back_end.repositories.DoctorRepository;
import com.project.back_end.repositories.PatientRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
public class TokenService {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    // Secret key from application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    public TokenService(AdminRepository adminRepository,
                        DoctorRepository doctorRepository,
                        PatientRepository patientRepository) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * ✅ Generate JWT token with 7-day expiration
     */
    public String generateToken(String identifier) {
        return Jwts.builder()
                .setSubject(identifier)  // email or username
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7))) // 7 days
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * ✅ Extract identifier (email/username) from token
     */
    public String extractIdentifier(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * ✅ Validate token against user type
     */
    public boolean validateToken(String token, String userType) {
        try {
            String identifier = extractIdentifier(token);

            if (userType.equalsIgnoreCase("admin")) {
                Admin admin = adminRepository.findByUsername(identifier);
                return admin != null;
            } else if (userType.equalsIgnoreCase("doctor")) {
                Doctor doctor = doctorRepository.findByEmail(identifier);
                return doctor != null;
            } else if (userType.equalsIgnoreCase("patient")) {
                Patient patient = patientRepository.findByEmail(identifier);
                return patient != null;
            }

            return false;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // token invalid or expired
        }
    }

    /**
     * ✅ Signing key from secret
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
