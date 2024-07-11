function getAuthToken() {
    // 토큰을 로컬 스토리지에서 가져옵니다.
    return localStorage.getItem('authToken');
}

function loadWishlist() {
    const token = getAuthToken();
    fetch('/api/wishes', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(wishes => {
            const wishlistTableBody = document.querySelector("#wishlistTableBody");
            wishlistTableBody.innerHTML = '';
            wishes.forEach(wish => {
                const row = document.createElement('tr');
                row.innerHTML = `
                <td>${wish.id}</td>
                <td>${wish.name}</td>
                <td>${wish.price}</td>
                <td>${wish.imageUrl}</td>
                <td>
                    <button class="btn btn-danger" onclick="deleteWish(${wish.id})">삭제</button>
                </td>
            `;
                wishlistTableBody.appendChild(row);
            });
        });
}

function createWish() {
    const productId = document.querySelector('#productId').value;
    const token = getAuthToken();

    fetch('/api/wishes', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ productId })
    }).then(response => {
        if (response.status === 201) {
            alert("위시리스트에 추가되었습니다.");
            loadWishlist();
        } else {
            alert("위시리스트에 추가되지 않았습니다.");
        }
    });
}

function deleteWish(id) {
    const token = getAuthToken();
    fetch(`/api/wishes`, {
        method: "DELETE",
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ productId : id })
    }).then(response => {
        if (response.status == 204) {
            alert("위시리스트에서 삭제되었습니다.");
            loadWishlist();
        } else {
            alert("위시리스트에서 삭제되지 않았습니다.");
        }
    });
}

document.addEventListener("DOMContentLoaded", () => {
    loadWishlist();
});
