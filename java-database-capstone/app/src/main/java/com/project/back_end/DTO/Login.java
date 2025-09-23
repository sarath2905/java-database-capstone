package com.project.back_end.dto;

/**
 * Data Transfer Object for login requests.
 * Used to receive user credentials (identifier + password) from the client.
 */
public class Login {

    private String identifier;  // email for Doctor/Patient, username for Admin
    private String password;

    public Login() {
        // Default constructor for deserialization
    }

    public Login(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    // Getters and Setters
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
