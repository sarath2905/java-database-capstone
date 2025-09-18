export function patientRow(patient) {
  return `
    <tr>
      <td>${patient.id}</td>
      <td>${patient.name}</td>
      <td>${patient.phone}</td>
      <td>${patient.email}</td>
      <td><button>View Prescription</button></td>
    </tr>
  `;
}
