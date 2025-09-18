// Example doctor card component
export function doctorCard(doctor) {
  const card = document.createElement("div");
  card.className = "doctor-card";
  card.innerHTML = `
    <h3>${doctor.name}</h3>
    <p>Specialty: ${doctor.specialty}</p>
    <p>Email: ${doctor.email}</p>
  `;
  return card;
}
