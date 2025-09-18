document.addEventListener("DOMContentLoaded", () => {
  const header = document.getElementById("header");
  if (header) {
    header.innerHTML = `
      <nav>
        <a href="/index.html">Home</a> |
        <a href="/pages/patientDashboard.html">Patients</a>
      </nav>
    `;
  }
});
