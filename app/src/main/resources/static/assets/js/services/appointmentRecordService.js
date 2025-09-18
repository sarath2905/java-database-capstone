// services/appointmentRecordService.js
import { API_BASE_URL } from "../config/config.js";

export async function getAppointments() {
  const res = await fetch(`${API_BASE_URL}/appointments`);
  return res.json();
}

export async function createAppointment(appointment) {
  const res = await fetch(`${API_BASE_URL}/appointments`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(appointment),
  });
  return res.json();
}

export async function updateAppointment(id, data) {
  const res = await fetch(`${API_BASE_URL}/appointments/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  return res.json();
}

export async function deleteAppointment(id) {
  return fetch(`${API_BASE_URL}/appointments/${id}`, { method: "DELETE" });
}
