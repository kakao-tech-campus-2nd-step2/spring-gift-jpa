document.addEventListener('DOMContentLoaded', function() {
    function handleFormSubmit(event, url, method) {
        event.preventDefault();

        const productData = {
            name: document.getElementById('name').value,
            price: document.getElementById('price').value,
            imageUrl: document.getElementById('imageUrl').value
        };

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        })
        .then(response => {
            if (response.ok) {
                window.location.href = '/admin/products';
            } else {
                return response.json().then(errorData => {
                    throw new Error(errorData.message || '상품을 처리하지 못하였습니다.');
                });
            }
        })
        .catch(error => {
            alert('Error: ' + error.message);
        });
    }

    // 상품 추가 폼
    if (document.getElementById('product-add-form')) {
        document.getElementById('product-add-form').addEventListener('submit', function(event) {
            handleFormSubmit(event, '/api/products', 'POST');
        });
    }

    // 상품 수정 폼
    if (document.getElementById('product-edit-form')) {
        document.getElementById('product-edit-form').addEventListener('submit', function(event) {
            const url = new URL(window.location.href);
            const pathSegments = url.pathname.split('/');
            const productId = pathSegments[pathSegments.length - 1];
            handleFormSubmit(event, `/api/products/${productId}`, 'PUT');
        });
    }
});

function deleteProduct(btn) {
    console.log(btn.dataset.productId);
    if(confirm("해당 상품을 삭제하시겠습니까?")) {
        fetch('/api/products/' + btn.dataset.productId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(response => {
            if (response.ok) {
                window.location.href = '/admin/products';
            } else {
                return response.json().then(errorData => {
                    throw new Error(errorData.message || '상품을 삭제하지 못하였습니다.');
                });
            }
        })
        .catch(error => {
            alert('Error: ' + error.message);
        });
    }
}
