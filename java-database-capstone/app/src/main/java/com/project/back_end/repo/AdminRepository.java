package com.project.back_end.repositories;

import com.project.back_end.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Admin entity.
 * Provides CRUD operations and custom queries.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Custom finder method to locate an Admin by username.
     * Spring Data JPA will automatically implement this query.
     *
     * @param username the admin's username
     * @return Admin entity if found, otherwise null
     */
    Admin findByUsername(String username);
}
