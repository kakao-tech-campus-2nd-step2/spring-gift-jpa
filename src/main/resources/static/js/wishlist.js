// Select/Deselect all checkboxes
document.getElementById('select-all').addEventListener('change', function(e) {
    const checkboxes = document.querySelectorAll('tbody input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        checkbox.checked = e.target.checked;
    });
});

document.getElementById('add-wish-product').addEventListener('click', function(event) {
    fetch(`/wishes/addWishProduct`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(errorText => {
                throw new Error(`Failed to fetch addWishProduct: ${errorText}`);
            });
        }
        return response.text();
    })
    .then(text => {
        document.open();
        document.write(text);
        document.close();
    })
    .catch(error => {
        console.error('Error:', error);
        alert(`An error occurred: ${error.message}`);
    });
});


document.getElementById('delete-selected').addEventListener('click', function(event) {
   const selectedCheckboxes = document.querySelectorAll('tbody input[type="checkbox"]:checked');
       selectedCheckboxes.forEach(checkbox => {
           const row = checkbox.closest('tr');
           const temp = row.getAttribute('data-id');
           console.log('Product ID:', temp);
           deleteCheckedProduct(temp);
       });

});

function deleteCheckedProduct(temp) {
    const data = {productId: temp};
    console.log(data);
    fetch(`/wishes`, {
        method: 'DELETE',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
    .then(response => {
            if (!response.ok) {
                return response.text().then(errorText => {
                    throw new Error(`Failed to fetch deleteProduct: ${errorText}`);
                });
            }
            return response.text();
        })
    .then(text => {
            document.open();
            document.write(text);
            document.close();
        })
    .catch(error => {
        console.error('Error:', error);
        alert(`An error occurred: ${error.message}`);
    });
}

function loadPage(pageNum) {
    fetch(`/wishes?page=${pageNum}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
    .then(response => {
            if (!response.ok) {
                return response.text().then(errorText => {
                    throw new Error(`Failed to fetch getWish: ${errorText}`);
                });
            }
            return response.text();
        })
    .then(html => {
            document.open();
            document.write(html);
            document.close();
        })
    .catch(error => {
        console.error('Error:', error);
        alert(`An error occurred: ${error.message}`);
    });
}
