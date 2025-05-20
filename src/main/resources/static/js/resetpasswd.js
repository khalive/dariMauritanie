
// Get reset password form elements
const resetPasswordForm = document.querySelector(".reset-password-form");
const resetPasswordTitle = document.querySelector(".title-reset-password");

// If you want to ensure the reset form is visible when the page loads
document.addEventListener("DOMContentLoaded", () => {
  if (resetPasswordForm) {
    resetPasswordForm.style.left = "50%";
    resetPasswordForm.style.opacity = "1";
  }

  if (resetPasswordTitle) {
    resetPasswordTitle.style.top = "50%";
    resetPasswordTitle.style.opacity = "1";
  }
});


 