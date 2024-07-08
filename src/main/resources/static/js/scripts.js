function createProducts() {
    const product = {
        id: document.querySelector('#productId').value,
        name: document.querySelector('#productName').value,
        price: document.querySelector('#productPrice').value,
        imageUrl: document.querySelector('#productImageUrl').value
    };

    fetch('/api/products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(product)
    }).then(response => {
        if (response.status == 201) {
            alert("상품이 생성되었습니다.");
            loadProducts();
        } else {
            alert("상품이 생성되지 않았습니다.");
        }
    });
}

function loadProducts() {
    fetch('/api/products')
        .then(response => response.json())
        .then(products => {
            const productsTableBody = document.querySelector("#productsTableBody");
            productsTableBody.innerHTML = '';
            products.forEach(product => {
                const row = document.createElement('tr');
                row.innerHTML = `
                        <td>${product.id}</td>
                        <td><span>${product.name}</span><input type="text" value="${product.name}" style="display:none"></td>
                        <td><span>${product.price}</span><input type="text" value="${product.price}" style="display:none"></td>
                        <td><span>${product.imageUrl}</span><input type="text" value="${product.imageUrl}" style="display:none"></td>
                        <td>
                            <button class="btn btn-warning" onclick="editProduct(${product.id}, this)">수정 버튼</button>
                            <button class="btn btn-primary" onclick="saveProduct(${product.id}, this)" style="display:none">저장 버튼</button>
                            <button class="btn btn-danger" onclick="deleteProduct(${product.id})">삭제 버튼</button>
                        </td>
                    `;
                productsTableBody.appendChild(row);
            });
        });
}

function editProduct(id, button) {
    const row = button.closest('tr');
    const spans = row.querySelectorAll('span');
    const inputs = row.querySelectorAll('input');
    spans.forEach(span => span.style.display = 'none');
    inputs.forEach(input => input.style.display = 'inline');
    button.style.display = 'none';
    row.querySelector('button[onclick^="saveProduct"]').style.display = 'inline';
}

function saveProduct(id, button) {
    const row = button.closest('tr');
    const inputs = row.querySelectorAll('input');
    const product = {
        id: id,
        name: inputs[0].value,
        price: inputs[1].value,
        imageUrl: inputs[2].value
    };

    fetch('/api/products', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(product)
    }).then(response => {
        if (response.status === 201) {
            alert('성공적으로 수정되었습니다');
            loadProducts();
        } else {
            alert('수정되지 않았습니다.');
        }
    });
}

function deleteProduct(id) {
    fetch(`/api/products/${id}`, {
        method: "DELETE"
    }).then(response => {
        if (response.status == 204) {
            alert("상품 삭제가 완료되었습니다.");
            loadProducts()
        } else {
            alert("상품 삭제가 되지 않았습니다.");
        }
    });
}

document.addEventListener("DOMContentLoaded", () => {
    loadProducts();
})
