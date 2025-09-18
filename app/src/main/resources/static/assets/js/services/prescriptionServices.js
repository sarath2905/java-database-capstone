// services/prescriptionServices.js
import { API_BASE_URL } from "../config/config.js";

export async function getPrescriptions() {
  const res = await fetch(`${API_BASE_URL}/prescriptions`);
  return res.json();
}

export async function addPrescription(prescription) {
  const res = await fetch(`${API_BASE_URL}/prescriptions`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(prescription),
  });
  return res.json();
}
