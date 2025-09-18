export function renderAppointments(appointments) {
  const container = document.getElementById("appointments");
  container.innerHTML = appointments.map(a => `<p>${a.date} - ${a.doctor}</p>`).join("");
}
