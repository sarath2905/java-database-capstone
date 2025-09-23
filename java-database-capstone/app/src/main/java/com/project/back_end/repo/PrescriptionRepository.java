package com.project.back_end.repositories;

import com.project.back_end.models.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Prescription entity stored in MongoDB.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface PrescriptionRepository extends MongoRepository<Prescription, String> {

    /**
     * Find prescriptions associated with a specific appointment.
     *
     * @param appointmentId The ID of the appointment
     * @return List of prescriptions linked to that appointment
     */
    List<Prescription> findByAppointmentId(Long appointmentId);
}
