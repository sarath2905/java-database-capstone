// services/doctorServices.js
import { API_BASE_URL } from "../config/config.js";

export async function getAllDoctors() {
  const res = await fetch(`${API_BASE_URL}/doctors`);
  return res.json();
}

export async function addDoctor(doctor) {
  const res = await fetch(`${API_BASE_URL}/doctors`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(doctor),
  });
  return res.json();
}
