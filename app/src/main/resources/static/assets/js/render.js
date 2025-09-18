// Render helpers for dynamic UI
export function renderHeader() {
  const header = document.getElementById("header");
  if (header) header.innerHTML = `<h1>Smart Clinic Management</h1>`;
}

export function renderFooter() {
  const footer = document.getElementById("footer");
  if (footer) footer.innerHTML = `<p>&copy; 2025 Smart Clinic</p>`;
}

// Run on load
document.addEventListener("DOMContentLoaded", () => {
  renderHeader();
  renderFooter();
});
