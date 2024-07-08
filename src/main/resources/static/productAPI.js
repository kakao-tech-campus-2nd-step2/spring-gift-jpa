let productAPIUrl = window.location.origin + '/api/products';

export function addProduct(page) {
  const product = {
    name: document.getElementById('productName').value,
    price: document.getElementById('productPrice').value,
    imageUrl: document.getElementById('productImageUrl').value,
  };

  fetch(`${productAPIUrl}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(product),
  }).then((response) => {
    if (response.status === 200) {
      pagination(page);
    }
    if (response.status === 400) {
      response.text().then((text) => {
        const productNameErrorDiv = document.getElementById(
            'product-name-error-message'
        );
        productNameErrorDiv.innerText = text;
        productNameErrorDiv.style.display = 'block';
      });
    }
  });
}

export function deleteProduct(id, page) {
  fetch(`${productAPIUrl}/${id}`, {
    method: 'DELETE',
  }).then((response) => {
    if (response.status === 200) {
      pagination(page);
    }
  });
}

window.deleteProduct = deleteProduct;

export function editProduct(id, page) {
  const product = {
    name: document.getElementById('productName').value,
    price: document.getElementById('productPrice').value,
    imageUrl: document.getElementById('productImageUrl').value,
  };

  fetch(`${productAPIUrl}/${id}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(product),
  }).then((response) => {
    if (response.status === 200) {
      pagination(page);
    }
    if (response.status === 400) {
      response.text().then((text) => {
        const productNameErrorDiv = document.getElementById(
            'product-name-error-message'
        );
        productNameErrorDiv.innerText = text;
        productNameErrorDiv.style.display = 'block';
      });
    }
  });
}

export function pagination(page) {
  const originalUrl = `${window.location.origin}/admin?page=${page}`;
  window.location.href = originalUrl;
}

window.pagination = pagination;
