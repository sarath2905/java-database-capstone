// services/patientServices.js
import { API_BASE_URL } from "../config/config.js";

export async function getAllPatients() {
  const res = await fetch(`${API_BASE_URL}/patients`);
  return res.json();
}

export async function addPatient(patient) {
  const res = await fetch(`${API_BASE_URL}/patients`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(patient),
  });
  return res.json();
}
