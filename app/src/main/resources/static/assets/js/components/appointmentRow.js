// Component to render a single appointment row
export function appointmentRow(appointment) {
  return `
    <tr>
      <td>${appointment.id || "-"}</td>
      <td>${appointment.doctor?.name || "Unknown Doctor"}</td>
      <td>${appointment.patient?.name || "Unknown Patient"}</td>
      <td>${appointment.appointmentTime || "-"}</td>
      <td>${appointment.status === 0 ? "Scheduled" : "Completed"}</td>
      <td>
        <button class="editBtn" data-id="${appointment.id}">Edit</button>
        <button class="deleteBtn" data-id="${appointment.id}">Delete</button>
      </td>
    </tr>
  `;
}
