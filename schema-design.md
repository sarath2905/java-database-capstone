# Smart Clinic Management System - Schema Design

## 1. MySQL Database Design
Below are the four core tables:  

---

### Patients Table
| Column       | Data Type     | Constraints                     |
|--------------|--------------|---------------------------------|
| patient_id   | INT AUTO_INCREMENT | PRIMARY KEY |
| name         | VARCHAR(100) | NOT NULL |
| email        | VARCHAR(100) | UNIQUE, NOT NULL |
| password     | VARCHAR(255) | NOT NULL |
| phone        | VARCHAR(15)  | UNIQUE |
| age          | INT          | CHECK(age >= 0) |

---

### Doctors Table
| Column       | Data Type     | Constraints                     |
|--------------|--------------|---------------------------------|
| doctor_id    | INT AUTO_INCREMENT | PRIMARY KEY |
| name         | VARCHAR(100) | NOT NULL |
| specialization | VARCHAR(100) | NOT NULL |
| email        | VARCHAR(100) | UNIQUE, NOT NULL |
| password     | VARCHAR(255) | NOT NULL |
| contact_info | VARCHAR(255) | NULL |

---

### Appointments Table
| Column        | Data Type     | Constraints |
|---------------|--------------|-------------|
| appointment_id| INT AUTO_INCREMENT | PRIMARY KEY |
| patient_id    | INT          | FOREIGN KEY REFERENCES Patients(patient_id) |
| doctor_id     | INT          | FOREIGN KEY REFERENCES Doctors(doctor_id) |
| date_time     | DATETIME     | NOT NULL |
| status        | ENUM('BOOKED', 'CANCELLED', 'COMPLETED') | DEFAULT 'BOOKED' |

---

### Admin Table
| Column       | Data Type     | Constraints                     |
|--------------|--------------|---------------------------------|
| admin_id     | INT AUTO_INCREMENT | PRIMARY KEY |
| username     | VARCHAR(50)  | UNIQUE, NOT NULL |
| password     | VARCHAR(255) | NOT NULL |
| email        | VARCHAR(100) | UNIQUE, NOT NULL |

---

### 📝 Design Justification
- **Normalization:** Separate tables for patients, doctors, and admins prevent redundancy.  
- **Constraints:** `UNIQUE`, `NOT NULL`, and `CHECK` ensure data quality.  
- **Relationships:** Appointments reference both patients and doctors, creating a many-to-many relationship resolved by the `appointments` table.  

---

## 2. MongoDB Collection Design

MongoDB is used for storing **unstructured data** such as **prescriptions**.  
This allows flexibility since prescriptions vary in medications, dosage, and notes.  

### Prescription Collection Example

```json
{
  "id": "64fa23ab56cd9012ef45",
  "appointmentId": 101,
  "doctorId": 5,
  "patientId": 12,
  "prescriptionDate": "2025-09-15",
  "medications": [
    {
      "name": "Paracetamol",
      "dosage": "500mg",
      "duration": "5 days"
    },
    {
      "name": "Amoxicillin",
      "dosage": "250mg",
      "duration": "7 days"
    }
  ],
  "notes": "Take after food. Drink plenty of water."
}
