package com.project.back_end.repositories;

import com.project.back_end.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Doctor entity.
 * Provides CRUD operations and advanced search queries.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Find a doctor by their email address.
     */
    Doctor findByEmail(String email);

    /**
     * Find doctors by partial name match (case-insensitive).
     */
    @Query("SELECT d FROM Doctor d " +
           "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> findByNameLike(String name);

    /**
     * Filter doctors by partial name (case-insensitive) and exact specialty (case-insensitive).
     */
    @Query("SELECT d FROM Doctor d " +
           "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "AND LOWER(d.specialty) = LOWER(:specialty)")
    List<Doctor> findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(String name, String specialty);

    /**
     * Find doctors by specialty (ignoring case).
     */
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);
}
