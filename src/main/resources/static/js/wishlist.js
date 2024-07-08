document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.add-to-wishlist').forEach(function(button) {
        button.addEventListener('click', function() {
            const productId = this.getAttribute('data-product-id');
            const quantity = this.previousElementSibling.value; // 수량 가져오기
            fetch('/web/wishlist/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({productId: productId, quantity: quantity})
            })
            .then(response => response.text())
            .then(data => alert(data))
            .catch(error => console.error('Error:', error));
        });
    });
});
