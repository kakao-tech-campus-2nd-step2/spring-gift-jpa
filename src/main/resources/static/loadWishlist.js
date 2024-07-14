document.addEventListener('DOMContentLoaded', function () {
      const authToken = localStorage.getItem('Authorization');
      if (!authToken) {
        window.location.href = '/members/login';
      } else {
        fetch('/wishlistPage', {
          method: 'GET',
          headers: {
            'Authorization': authToken
          }
        })
        .then(response => {
          if (response.ok) {
            response.text().then(
                html => {
                  document.open()
                  document.write(html)
                  document.close()
                })
          }
        })
      }
    }
)