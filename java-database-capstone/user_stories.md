# Smart Clinic Management System (SCMS) - User Stories

This document captures user stories for Admin, Patient, and Doctor roles using a structured template.  

---

## Admin User Stories

### 1. Admin Login  
_As an admin, I want to log into the portal with my username and password, so that I can manage the platform securely._  

**Acceptance Criteria:**  
1. Admin enters valid username and password.  
2. Successful login grants access to the admin dashboard.  
3. Invalid credentials display an error message.  

**Priority:** High  
**Story Points:** 2  
**Notes:** Use secure authentication (e.g., hashed passwords).  

---

### 2. Admin Logout  
_As an admin, I want to log out of the portal, so that system access is protected._  

**Acceptance Criteria:**  
1. Admin can log out from the dashboard.  
2. Session is terminated after logout.  
3. Redirect to the login page after logout.  

**Priority:** High  
**Story Points:** 1  
**Notes:** Auto logout after inactivity is recommended.  

---

### 3. Add Doctor  
_As an admin, I want to add doctors to the portal, so that they can be available for patients._  

**Acceptance Criteria:**  
1. Admin can enter doctor’s details (name, specialization, contact info).  
2. Doctor account is created and stored in the system.  
3. Confirmation is sent to the doctor.  

**Priority:** High  
**Story Points:** 3  
**Notes:** Verification of doctor credentials may be required.  

---

### 4. Delete Doctor  
_As an admin, I want to delete a doctor’s profile, so that the system only lists active doctors._  

**Acceptance Criteria:**  
1. Admin can search and select a doctor profile.  
2. Doctor profile is permanently removed from the database.  
3. Any future appointments for the doctor are canceled.  

**Priority:** Medium  
**Story Points:** 3  
**Notes:** Add a confirmation step before deletion.  

---

### 5. Track Appointments via MySQL Stored Procedure  
_As an admin, I want to run a stored procedure in MySQL CLI, so that I can get the number of appointments per month and track usage statistics._  

**Acceptance Criteria:**  
1. Stored procedure calculates appointments per month.  
2. Output is displayed clearly in MySQL CLI.  
3. Can be run anytime for updated statistics.  

**Priority:** Medium  
**Story Points:** 4  
**Notes:** Procedure should aggregate data efficiently.  

---

## Patient User Stories

### 6. View Doctors Without Login  
_As a patient, I want to view a list of doctors without logging in, so that I can explore options before registering._  

**Acceptance Criteria:**  
1. Patient can see available doctors.  
2. Only basic info (name, specialization) is shown.  
3. Booking requires login.  

**Priority:** Medium  
**Story Points:** 2  
**Notes:** Profile contact details should be hidden until login.  

---

### 7. Patient Sign Up  
_As a patient, I want to sign up using my email and password, so that I can book appointments._  

**Acceptance Criteria:**  
1. Patient can register with unique email and password.  
2. Confirmation email is sent after signup.  
3. User data is stored securely.  

**Priority:** High  
**Story Points:** 3  
**Notes:** Use strong password validation.  

---

### 8. Patient Login  
_As a patient, I want to log into the portal, so that I can manage my bookings._  

**Acceptance Criteria:**  
1. Login with valid email and password.  
2. Access to patient dashboard is granted.  
3. Invalid credentials show an error message.  

**Priority:** High  
**Story Points:** 2  
**Notes:** JWT tokens/session-based auth recommended.  

---

### 9. Patient Logout  
_As a patient, I want to log out of the portal, so that my account remains secure._  

**Acceptance Criteria:**  
1. Patient can log out at any time.  
2. Session is terminated.  
3. Redirect to login page.  

**Priority:** High  
**Story Points:** 1  
**Notes:** Auto logout after inactivity is recommended.  

---

### 10. Book Appointment  
_As a patient, I want to log in and book an hour-long appointment, so that I can consult with a doctor._  

**Acceptance Criteria:**  
1. Patient selects a doctor and an available slot.  
2. Appointment is booked for exactly one hour.  
3. Confirmation is sent to both doctor and patient.  

**Priority:** High  
**Story Points:** 4  
**Notes:** Handle double-booking conflicts.  

---

### 11. View Upcoming Appointments  
_As a patient, I want to view my upcoming appointments, so that I can prepare accordingly._  

**Acceptance Criteria:**  
1. Patient can see a list of upcoming confirmed appointments.  
2. Appointment details include date, time, and doctor name.  
3. Past appointments are not listed.  

**Priority:** Medium  
**Story Points:** 2  
**Notes:** Optionally add reschedule/cancel features.  

---

## Doctor User Stories

### 12. Doctor Login  
_As a doctor, I want to log into the portal, so that I can manage my appointments._  

**Acceptance Criteria:**  
1. Doctor enters valid email and password.  
2. Dashboard shows upcoming appointments.  
3. Invalid login shows error.  

**Priority:** High  
**Story Points:** 2  
**Notes:** Secure authentication required.  

---

### 13. Doctor Logout  
_As a doctor, I want to log out of the portal, so that my data is protected._  

**Acceptance Criteria:**  
1. Doctor can log out anytime.  
2. Session is terminated.  
3. Redirect to login page.  

**Priority:** High  
**Story Points:** 1  
**Notes:** Auto logout recommended.  

---

### 14. View Appointment Calendar  
_As a doctor, I want to view my appointment calendar, so that I can stay organized._  

**Acceptance Criteria:**  
1. Doctor can see appointments in calendar view.  
2. Appointments show patient name, date, and time.  
3. Calendar updates in real-time.  

**Priority:** High  
**Story Points:** 4  
**Notes:** Integration with Google Calendar optional.  

---

### 15. Mark Unavailability  
_As a doctor, I want to mark my unavailability, so that patients only book during available slots._  

**Acceptance Criteria:**  
1. Doctor can block specific dates/times.  
2. Patients cannot book during unavailable slots.  
3. Changes reflect immediately.  

**Priority:** Medium  
**Story Points:** 3  
**Notes:** Support recurring unavailable days (holidays, leave).  

---

### 16. Update Profile  
_As a doctor, I want to update my profile with specialization and contact information, so that patients have up-to-date details._  

**Acceptance Criteria:**  
1. Doctor can edit specialization and contact info.  
2. Updates reflect on patient-facing profiles.  
3. Changes are saved securely.  

**Priority:** Medium  
**Story Points:** 2  
**Notes:** Admin may need to approve major changes.  

---

### 17. View Patient Details  
_As a doctor, I want to view patient details for upcoming appointments, so that I can be prepared._  

**Acceptance Criteria:**  
1. Doctor can view basic patient info (name, age, contact).  
2. Doctor can see patient’s medical history if available.  
3. Data is accessible only for confirmed appointments.  

**Priority:** High  
**Story Points:** 4  
**Notes:** Must comply with privacy/security policies.  

---
