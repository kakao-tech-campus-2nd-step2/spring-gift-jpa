let currentPage = 0;
const pageSize = 5;

function createProducts() {
    const product = {
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
            loadProducts(currentPage);
        } else {
            alert("상품이 생성되지 않았습니다.");
        }
    });
}

function loadProducts(page) {
    fetch(`/api/products?page=${page}&size=${pageSize}`)
        .then(response => response.json())
        .then(products => {
            const productsTableBody = document.querySelector("#productsTableBody");
            productsTableBody.innerHTML = '';
            products.content.forEach(product => {
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

            renderPagination(products.page.totalPages, page);
        });
}

function renderPagination(totalPages, currentPage) {
    const paginationNav = document.querySelector("#paginationNav");
    paginationNav.innerHTML = '';

    const ul = document.createElement('ul');
    ul.classList.add('pagination');

    const prevLi = document.createElement('li');
    prevLi.classList.add('page-item');
    const prevBtn = document.createElement('a');
    prevBtn.classList.add('page-link');
    prevBtn.href = '#';
    prevBtn.textContent = '이전';
    prevBtn.onclick = () => {
        const prevPage = currentPage - 1;
        if (prevPage >= 0) {
            loadProducts(prevPage);
        }
    };
    prevLi.appendChild(prevBtn);
    ul.appendChild(prevLi);

    for (let i = 0; i < totalPages; i++) {
        const li = document.createElement('li');
        li.classList.add('page-item');
        const btn = document.createElement('a');
        btn.classList.add('page-link');
        btn.href = '#';
        btn.textContent = `${i + 1}`;
        btn.onclick = (index => () => {
            loadProducts(index);
        })(i);
        if (i === currentPage) {
            li.classList.add('active');
        }
        li.appendChild(btn);
        ul.appendChild(li);
    }

    const nextLi = document.createElement('li');
    nextLi.classList.add('page-item');
    const nextBtn = document.createElement('a');
    nextBtn.classList.add('page-link');
    nextBtn.href = '#';
    nextBtn.textContent = '다음';
    nextBtn.onclick = () => {
        const nextPage = currentPage + 1;
        if (nextPage < totalPages) {
            loadProducts(nextPage);
        }
    };
    nextLi.appendChild(nextBtn);
    ul.appendChild(nextLi);

    paginationNav.appendChild(ul);
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
            loadProducts(currentPage);
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
            loadProducts(currentPage)
        } else {
            alert("상품 삭제가 되지 않았습니다.");
        }
    });
}

document.addEventListener("DOMContentLoaded", () => {
    loadProducts(currentPage);
})
