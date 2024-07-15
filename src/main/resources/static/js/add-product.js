document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('product-addition-form');

    form.addEventListener('submit', event => {
        event.preventDefault();
        addProduct();
    });

    function addProduct() {
        const newProduct = {
            name: document.getElementById('product-name').value,
            price: parseFloat(document.getElementById('product-price').value),
            imageUrl: document.getElementById('product-image').value
        };

        fetch('/api/products', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newProduct)
        })
            .then(response => {
                if (response.ok) {
                    alert('상품 추가 성공!');
                    window.location.href = '/page/manage/products';
                } else {
                    alert('상품 추가 실패.');
                }
            })
            .catch(error => console.error('[Error]상품 추가 에러:', error));
    }
});