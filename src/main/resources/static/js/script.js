function submitEditForm(productId) {
  const form = document.getElementById('editProductForm');
  const formData = new FormData(form);

  const data = {};
  formData.forEach((value, key) => {
    data[key] = value;
  });

  console.log('Form data:', data);

  fetch('/api/products/' + productId, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('알 수 없는 에러가 발생했습니다! ' + response.statusText);
    }
    return response.json();
  })
  .then(data => {
    alert('상품 수정이 완료되었습니다!');
    console.log(data);
    window.location.href = '/view/products';
  })
  .catch(error => {
    console.error('알 수 없는 에러가 발생했습니다! ', error);
  });
}

function submitAddForm() {
  const form = document.getElementById('addProductForm');
  const formData = new FormData(form);

  const data = {};
  formData.forEach((value, key) => {
    data[key] = value;
  });

  console.log('Form data:', data);

  fetch('/api/products', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('알 수 없는 에러가 발생했습니다! ' + response.statusText);
    }
    return response.json();
  })
  .then(data => {
    alert('상품 추가가 완료되었습니다!');
    console.log(data);
    window.location.href = '/view/products';
  })
  .catch(error => {
    console.error('알 수 없는 에러가 발생했습니다! ', error);
  });
}

function deleteProductById(productId) {
  fetch('/api/products/' + productId, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('알 수 없는 에러가 발생했습니다! ' + response.statusText);
    }
    alert('상품 삭제가 완료되었습니다!');
    window.location.href = '/view/products';
  })
  .catch(error => {
    console.error('알 수 없는 에러가 발생했습니다! ', error);
  });
}
