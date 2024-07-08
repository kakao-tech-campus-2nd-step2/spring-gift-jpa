function openAddModal() {
  var modal = document.getElementById("addModal");
  modal.style.display = "block";
}

function closeAddModal() {
  var modal = document.getElementById("addModal");
  modal.style.display = "none";
}

function openUpdateModal(id) {
  console.log(id);
  var modal = document.getElementById("updateModal");
  document.getElementById("updateId").value = parseInt(id);
  modal.style.display = "block";
}

function closeUpdateModal() {
  var modal = document.getElementById("updateModal");
  modal.style.display = "none";
}

window.onclick = function (event) {
  var modal = document.getElementById("addModal");
  if (event.target === modal) {
    modal.style.display = "none";
  }
  var updateModal = document.getElementById("updateModal");
  if (event.target === updateModal) {
    updateModal.style.display = "none";
  }
}

function submitAddForm() {
  var form = document.getElementById('addProductForm');
  var formData = new FormData(form);
  var jsonObject = {};
  formData.forEach((value, key) => {
    jsonObject[key] = value;
  });

  fetch('/api/products', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(jsonObject)
  })
  .then(response => {
    if (response.ok) {
      closeAddModal();
      window.location.reload();
    } else {
      console.error('Error:', response.statusText);
    }
  })
  .catch(error => {
    console.error('Error:', error);
  });
}

function submitUpdateForm() {
  var form = document.getElementById('updateProductForm');
  var formData = new FormData(form);
  var jsonObject = {};
  formData.forEach((value, key) => {
    jsonObject[key] = value;
  });

  fetch('/api/products', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(jsonObject)
  })
  .then(response => {
    if (response.ok) {
      closeUpdateModal();
      window.location.reload();
    } else {
      console.error('Error:', response.statusText);
    }
  })
  .catch(error => {
    console.error('Error:', error);
  });
}

function deleteProduct(id) {
  if (confirm('정말로 이 제품을 삭제하시겠습니까?')){
    fetch('/api/products/' + id, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      if (response.ok) {
        window.location.reload();
      } else {
        console.error('Error:', response.statusText);
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
  }
}