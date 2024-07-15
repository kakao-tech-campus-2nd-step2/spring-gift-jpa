document.addEventListener('DOMContentLoaded', () => {
    loadProducts();

    function loadProducts() {
        fetch('/api/products')
            .then(response => response.json())
            .then(products => {
                const productList = document.getElementById('product-list');
                productList.innerHTML = '';
                products.forEach(product => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                        <td><img src="${product.imageUrl}" alt="Image" width="50"/></td>
                        <td>
                            <a href="/page/manage/products/update/${product.id}">Edit</a>
                            <button onclick="deleteProduct(${product.id})">Delete</button>
                        </td>
                    `;
                    productList.appendChild(row);
                });
            });
    }

    window.deleteProduct = function(productId) {
        if (confirm('상품을 삭제합니다.')) {
            fetch(`/api/products/${productId}`, {
                method: 'DELETE',
            })
                .then(response => {
                    if (response.ok) {
                        alert('상품 삭제 성공!');
                        loadProducts();
                    } else {
                        alert('상품 삭제 실패.');
                    }
                })
                .catch(error => console.error('[Error]상품 삭제 에러:', error));
        }
    }
});
