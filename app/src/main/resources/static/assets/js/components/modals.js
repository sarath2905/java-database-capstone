export function openModal(content) {
  const modal = document.getElementById("modal");
  const body = document.getElementById("modal-body");
  body.innerHTML = content;
  modal.style.display = "block";
}

export function closeModal() {
  document.getElementById("modal").style.display = "none";
}

document.addEventListener("DOMContentLoaded", () => {
  document.getElementById("closeModal")?.addEventListener("click", closeModal);
});
