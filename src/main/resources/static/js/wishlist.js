document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('token');
    if (!token) {
        alert('로그인이 필요합니다.');
        window.location.href = '/login';
        return;
    }

    fetch(`/wishes`, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        // 데이터를 사용하여 페이지를 업데이트하는 코드
    })
    .catch(error => {
        console.error('Error:', error);
        alert('인증 오류가 발생했습니다. 다시 로그인 해주세요.');
        window.location.href = '/login';
    });

document.getElementById('add-wish-product').addEventListener('click', function() {
        window.location.href = '/wishes/addWishProduct';
    });