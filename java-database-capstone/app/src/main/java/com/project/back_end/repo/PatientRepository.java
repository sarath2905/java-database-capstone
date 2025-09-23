package com.project.back_end.repositories;

import com.project.back_end.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Patient entity.
 * Provides CRUD operations and search methods by email or phone.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Find a patient by their email address.
     */
    Patient findByEmail(String email);

    /**
     * Find a patient by either email or phone number.
     */
    Patient findByEmailOrPhone(String email, String phone);
}
