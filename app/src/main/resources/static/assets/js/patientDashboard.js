document.addEventListener("DOMContentLoaded", () => {
  const list = document.getElementById("doctorList");
  list.innerHTML = `
    <div><h3>Dr. Smith</h3><p>Cardiology</p></div>
    <div><h3>Dr. Jane</h3><p>Dermatology</p></div>
  `;
});
