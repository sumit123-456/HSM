 
    const form = document.getElementById('loginForm');
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const emailError = document.getElementById('emailError');
    const passwordError = document.getElementById('passwordError');

    form.addEventListener('submit', function(e) {
      let valid = true;
      emailError.textContent = '';
      passwordError.textContent = '';

      const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
      if (!email.value.match(emailPattern)) {
        emailError.textContent = 'Please enter a valid email';
        valid = false;
      }

      const passwordValue = password.value;
      const passwordRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.{8,})/;
      if (!passwordRegex.test(passwordValue)) {
        passwordError.textContent = "Password must be of 8+ characters, include 1 uppercase & 1 special character.";
        valid = false;
      }

      if (!valid) e.preventDefault();
    });
   