document.addEventListener('DOMContentLoaded', function() {
    const rememberMeCheckbox = document.getElementById('rememberMe');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const errorMessage = document.getElementById('errorMessage');
    
    // Sayfa yüklendiğinde localStorage'daki bilgileri kontrol et
    if (localStorage.getItem('rememberMe') === 'true') {
        rememberMeCheckbox.checked = true;
    }

    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Formun varsayılan submit işlemini engeller

        const username = usernameInput.value;
        const password = passwordInput.value;

        const credentials = {
            login: username,
            password: password
        };

        fetch('http://localhost:8080/loginClient', {
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
        .then(clientDTO => {
            // Remember Me işaretli ise token'ı sakla
            if (rememberMeCheckbox.checked) {
                localStorage.setItem('rememberMe', true);
            } else {
                localStorage.setItem('rememberMe', false);
            }

            localStorage.setItem('token', clientDTO.token);
            localStorage.setItem('role', clientDTO.role);
            localStorage.setItem('CL_name', clientDTO.name);

        })
        .then( () => {
            // Kullanıcıyı yönlendir
            window.location.href = '../CL_PAGE/index_client.html';
        })
        .catch(error => {
            errorMessage.style.display = 'block'; // Hata mesajı göster
            console.error('Error:', error);
        });
    });
});
