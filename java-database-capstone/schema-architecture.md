# Smart Clinic Management System - Architecture Design

## 1. Architecture Summary

The Smart Clinic Management System follows a **three-tier web application architecture** using **Spring Boot**.  

- **Presentation Tier**  
  - Uses **Thymeleaf templates** for server-side rendering of Admin and Doctor dashboards.  
  - Uses **REST APIs** for patient-facing modules (appointments, patient records, patient dashboards).  

- **Application Tier**  
  - Built with **Spring Boot** using both **MVC Controllers** (for Thymeleaf pages) and **REST Controllers** (for JSON APIs).  
  - Controllers delegate all business logic to the **Service Layer**, which applies rules, validates data, and coordinates workflows.  

- **Data Tier**  
  - **MySQL** stores structured relational data such as Patients, Doctors, Appointments, and Admins.  
  - **MongoDB** stores unstructured, document-based data such as Prescriptions.  
  - Data access is handled through the **Repository Layer** using Spring Data JPA (for MySQL) and Spring Data MongoDB (for prescriptions).  

This dual-database approach leverages the strengths of both relational and document-based storage.  
The system is designed for scalability, maintainability, and CI/CD compatibility, with deployment possible via Docker/Kubernetes.  

---

## 2. Numbered Flow of Data and Control

1. **User Interface Layer**  
   - Admins and Doctors access dashboards rendered with Thymeleaf templates.  
   - Patients and external clients use REST API calls (via HTTP/JSON).  

2. **Controller Layer**  
   - Thymeleaf Controllers handle web page requests and return `.html` templates.  
   - REST Controllers handle API requests and return JSON responses.  

3. **Service Layer**  
   - Controllers delegate to services.  
   - Services apply business logic (e.g., check doctor availability before scheduling).  
   - Services act as the “heart” of the backend and separate logic from data access.  

4. **Repository Layer**  
   - Services call repositories for data access.  
   - MySQL repositories (via Spring Data JPA) manage structured entities like patients, doctors, and appointments.  
   - MongoDB repositories (via Spring Data MongoDB) manage prescriptions and other flexible data.  

5. **Database Access**  
   - MySQL enforces relational structure for consistency and constraints.  
   - MongoDB allows flexible storage for medical records/prescriptions that may vary in format.  

6. **Model Binding**  
   - Retrieved database records are mapped to **Java model classes**.  
   - MySQL → JPA Entities (`@Entity`).  
   - MongoDB → Document Models (`@Document`).  

7. **Response Generation**  
   - For MVC flows: Models are passed to Thymeleaf templates → rendered as HTML pages.  
   - For REST flows: Models (or DTOs) are serialized into JSON → returned in API responses.  

---

## 3. Key Technologies

- **Backend:** Spring Boot, Spring MVC, Spring REST  
- **Frontend:** Thymeleaf (Admin/Doctor dashboards), REST API consumers  
- **Databases:** MySQL (relational), MongoDB (document-based)  
- **Persistence:** Spring Data JPA, Spring Data MongoDB  
- **Deployment:** Docker / Kubernetes (cloud-ready), GitHub Actions CI/CD  

---

## 4. Summary

The architecture ensures a clean separation of concerns across layers.  
- MVC and REST coexist in one Spring Boot project.  
- MySQL ensures structured clinic data integrity, while MongoDB provides flexibility for medical prescriptions.  
- The architecture supports both web dashboards and API-driven integrations (mobile apps, third-party systems).  

This design balances **scalability, extensibility, and maintainability**, making it suitable for real-world clinic management.
✅ This matches the lab instructions (architecture summary + 7-step flow).
