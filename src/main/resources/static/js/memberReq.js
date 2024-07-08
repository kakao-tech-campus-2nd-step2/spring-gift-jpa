function register() {

    event.preventDefault();

    var formData = {
        'email' : $('#email').val(),
        'password' : $('#password').val()
    };

    $.ajax({
        url: '/api/member/register',
        method: 'POST',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        processData: false,
        success: function (response) {
            alert(response);
            location.href = '/';
        },
        error: function (request, status, error) {
            alert(request.responseText);            
        }
    });
}


$(document).ready(function() {
    var token = localStorage.getItem("token");
    if (token) {
        updateUI(true);
    } else {
        updateUI(false);
    }
//    getWishlist();
});

function login() {

    event.preventDefault();

    var formData = {
        'email' : $('#email').val(),
        'password' : $('#password').val()
    };

    $.ajax({
        url: '/api/member/login',
        method: 'POST',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        processData: false,
        success: function (data, status, jqXHR) {
            const token = jqXHR.getResponseHeader('Token');
            localStorage.setItem("token", token);
            console.log(token);
            alert(data);
            location.href = '/';
            updateUI(true);
        },
        error: function (request, status, error) {
            alert(request.responseText);
        }
    });
}

function logout() {
    localStorage.removeItem('token');
    updateUI(false);
}

function updateUI(isLoggedIn) {
    if (isLoggedIn) {
        $('#login-button').hide(); 
        $('#logout-button').show();
    } else {
        $('#login-button').show();
        $('#logout-button').hide(); 
    }
}



function getWishlist() {
    $.ajax({
        url: "/api/member/wishlist",
        type: "GET",
        headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        success: function(data) {
            var productList = $('#wish-list');
            productList.empty();
            $.each(data, function(index, object) {
                var product = '<tr>';
                product += '<td>' + object.id + '</td>';
                product += '<td>' + object.name + '</td>';
                product += '<td>' + object.price + '</td>';
                product += '<td>';
                product += '<a href="' + object.imageUrl + '">';
                product += '<img src="' + object.imageUrl + '" alt="Not found image" style="width: 40px; height: 40px;" />';
                product += '</a>';
                product += '</td>';
                product += '<td>';
                product += '<button class="delete-button btn btn-danger" data-product-id="' + object.id + '">삭제</button>';
                product += '</td>';
                product += '</tr>';
                productList.append(product);
            });
            
        },
        error: function (request, status, error) {
            alert(request.responseText);
        }
    });
}

$(document).on('click', '.delete-button', function() {
    var productId = $(this).data('product-id');
    deleteWishlist(productId);
});

function deleteWishlist(productId) {
    $.ajax({
        url: `/api/member/wishlist/${productId}`,
        type: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        success: function(response) {
            alert(response);
        },
        error: function (request, status, error) {
            alert(request.responseText);
        }
    });
}