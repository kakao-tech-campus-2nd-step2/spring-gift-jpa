// Select/Deselect all checkboxes
document.getElementById('select-all').addEventListener('change', function(e) {
    const checkboxes = document.querySelectorAll('tbody input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        checkbox.checked = e.target.checked;
    });
});

// Delete selected rows
document.getElementById('delete-selected').addEventListener('click', function() {
    const selectedCheckboxes = document.querySelectorAll('tbody input[type="checkbox"]:checked');
    selectedCheckboxes.forEach(checkbox => {
        const row = checkbox.closest('tr');
        const id = row.getAttribute('data-id');
        deleteProduct(id); // 서버에서 해당 제품을 삭제
        row.remove(); // 화면에서 행을 제거
    });
});

// Delete a row
document.querySelectorAll('.delete-btn').forEach(button => {
    button.addEventListener('click', function() {
        const id = this.getAttribute('data-id');
        deleteProduct(id); // 서버에서 해당 제품을 삭제
        const row = this.closest('tr'); // 버튼이 속한 가장 가까운 tr 요소를 찾음
        row.remove(); // 화면에서 행을 제거
    });
});

// Function to delete a product
function deleteProduct(id) {
    fetch(`/v3/products/${id}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        console.log(`Product with ID ${id} deleted.`);
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}

//제품 추가 모달 창 생성 시 에러 블록 초기화
document.querySelector('.add-btn').addEventListener('click', function() {
    // 모달 창을 다시 열 때 에러 블록 초기화
    const errorMessageElement = document.getElementById('addProductErrorMessage');
    errorMessageElement.style.display = 'none';
});

// Handle form submission for add
document.getElementById('addProductForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 폼 제출 동작을 막음

    const form = event.target;
    const data = {
        id: form.id.value,
        name: form.name.value,
        price: form.price.value,
        imageUrl: form.imageUrl.value
    };

    fetch(`/v3/products`, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        }
    })

    .then(response => {
            if (response.status == 400) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message);
                });
            }
    })

    .then(result => {
        // 모달 닫기
        $('#addProduct').modal('hide');
        window.location.href = "/v3/products";
    })

     .catch(error => {
            // 에러 메시지를 모달 창에 띄우기
            const errorMessageElement = document.getElementById('addProductErrorMessage');
            errorMessageElement.innerText = error.message;
            errorMessageElement.style.display = 'block';
     });

});

// Variable to store the original ID for editing
var originalId;

// Populate the form with product data for editing
document.querySelectorAll('.edit-btn').forEach(button => {
    button.addEventListener('click', function() {
        //모달 창을 다시 열 때 에러 블록 초기화
        const errorMessageElement = document.getElementById('editProductErrorMessage');
        errorMessageElement.style.display = 'none';

        originalId = this.dataset.id;
        const name = this.dataset.name;
        const price = this.dataset.price;
        const imageUrl = this.dataset.imageurl;

        const form = document.getElementById('editProductForm');
        form.name.value = name;
        form.price.value = price;
        form.imageUrl.value = imageUrl;
    });
});

// Handle form submission for editing
document.getElementById('editProductForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 폼 제출 동작을 막음

    const form = event.target;
    const data = {
        name: form.name.value,
        price: form.price.value,
        imageUrl: form.imageUrl.value
    };

    fetch(`/v3/products/${originalId}`, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        }
    })

    .then(response => {
            if (response.status === 400) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message);
                });
            }
        })

    .then(result => {
        // 모달 닫기
        $('#editProduct').modal('hide');
        window.location.href = "/v3/products";
    })

     .catch(error => {
            // 에러 메시지를 모달 창에 띄우기
            const errorMessageElement = document.getElementById('editProductErrorMessage');
            errorMessageElement.innerText = error.message;
            errorMessageElement.style.display = 'block';
        });

});
