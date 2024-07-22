document.addEventListener('DOMContentLoaded', function() {
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const errorMessage = document.getElementById('errorMessage');

    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Formun varsayılan submit işlemini engeller

        const username = usernameInput.value;
        const password = passwordInput.value;

        const credentials = {
            login: username,
            password: password
        };

        fetch('http://localhost:8080/loginAdmin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(credentials)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Login failed');
            }
            return response.json();
        })
        .then(adminDTO => {
            localStorage.setItem('adminId', adminDTO.id);
            localStorage.setItem('adminToken', adminDTO.token);
            localStorage.setItem('adminRole', adminDTO.role);
            localStorage.setItem('adminUsername', adminDTO.login);
            window.location.href = '../ADMIN_PANEL/index.html';
        })
        .catch(error => {
            errorMessage.style.display = 'block'; // Hata mesajı göster
            console.error('Error:', error);
        });
    });
});
