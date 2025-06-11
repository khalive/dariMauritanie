// Get form elements and titles
const loginForm = document.querySelector(".login-form");
const registerForm = document.querySelector(".register-form");
const forgotPasswordForm = document.querySelector(".forgot-password-form");
const resetPasswordForm = document.querySelector(".reset-password-form");
const wrapper = document.querySelector(".wrapper");

const loginTitle = document.querySelector(".title-login");
const registerTitle = document.querySelector(".title-register");
const forgotPasswordTitle = document.querySelector(".title-forgot-password");
const resetPasswordTitle = document.querySelector(".title-reset-password");

// Functions to switch forms
function loginFunction() {
  loginForm.style.left = "50%";
  loginForm.style.opacity = 1;

  registerForm.style.left = "150%";
  registerForm.style.opacity = 0;

  forgotPasswordForm.style.left = "150%";
  forgotPasswordForm.style.opacity = 0;

  resetPasswordForm.style.left = "150%";
  resetPasswordForm.style.opacity = 0;

  wrapper.style.height = "500px";

  loginTitle.style.top = "50%";
  loginTitle.style.opacity = 1;
  registerTitle.style.top = "50px";
  registerTitle.style.opacity = 0;
  forgotPasswordTitle.style.top = "-60px";
  forgotPasswordTitle.style.opacity = 0;
  resetPasswordTitle.style.top = "-60px";
  resetPasswordTitle.style.opacity = 0;
}

function registerFunction() {
  loginForm.style.left = "-50%";
  loginForm.style.opacity = 0;

  registerForm.style.left = "50%";
  registerForm.style.opacity = 1;

  forgotPasswordForm.style.left = "150%";
  forgotPasswordForm.style.opacity = 0;

  resetPasswordForm.style.left = "150%";
  resetPasswordForm.style.opacity = 0;

  wrapper.style.height = "580px";

  loginTitle.style.top = "-60px";
  loginTitle.style.opacity = 0;
  registerTitle.style.top = "50%";
  registerTitle.style.opacity = 1;
  forgotPasswordTitle.style.top = "-60px";
  forgotPasswordTitle.style.opacity = 0;
  resetPasswordTitle.style.top = "-60px";
  resetPasswordTitle.style.opacity = 0;
}

function forgotPasswordFunction() {
  loginForm.style.left = "150%";
  loginForm.style.opacity = 0;

  registerForm.style.left = "150%";
  registerForm.style.opacity = 0;

  forgotPasswordForm.style.left = "50%";
  forgotPasswordForm.style.opacity = 1;

  resetPasswordForm.style.left = "150%";
  resetPasswordForm.style.opacity = 0;

  wrapper.style.height = "500px";

  loginTitle.style.top = "-60px";
  loginTitle.style.opacity = 0;
  registerTitle.style.top = "-60px";
  registerTitle.style.opacity = 0;
  forgotPasswordTitle.style.top = "50%";
  forgotPasswordTitle.style.opacity = 1;
  resetPasswordTitle.style.top = "-60px";
  resetPasswordTitle.style.opacity = 0;
}

function resetPasswordFunction() {
  loginForm.style.left = "150%";
  loginForm.style.opacity = 0;

  registerForm.style.left = "150%";
  registerForm.style.opacity = 0;

  forgotPasswordForm.style.left = "150%";
  forgotPasswordForm.style.opacity = 0;

  resetPasswordForm.style.left = "50%";
  resetPasswordForm.style.opacity = 1;

  wrapper.style.height = "500px";

  loginTitle.style.top = "-60px";
  loginTitle.style.opacity = 0;
  registerTitle.style.top = "-60px";
  registerTitle.style.opacity = 0;
  forgotPasswordTitle.style.top = "-60px";
  forgotPasswordTitle.style.opacity = 0;
  resetPasswordTitle.style.top = "50%";
  resetPasswordTitle.style.opacity = 1;
}

// Event listener for forgot password link
document
  .querySelector("#ForgotPasswordLink")
  .addEventListener("click", forgotPasswordFunction);

