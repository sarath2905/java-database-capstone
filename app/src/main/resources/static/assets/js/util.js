// Utility functions
export function $(selector) {
  return document.querySelector(selector);
}

export function createElement(tag, className = "", text = "") {
  const el = document.createElement(tag);
  if (className) el.className = className;
  if (text) el.textContent = text;
  return el;
}
