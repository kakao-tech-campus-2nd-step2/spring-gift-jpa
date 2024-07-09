$(document).ready(function() {
    $("#addProductBtn").click(function() {
        $("#addProductModal").dialog({
            modal: true,
            width: 400,
            buttons: {
                "Add": function() {
                    let name = $("#name").val();
                    let price = $("#price").val();
                    let imageUrl = $("#imageUrl").val();

                    if (name && price && imageUrl) {
                        let newProduct = {
                            name: name,
                            price: price,
                            imageUrl: imageUrl
                        };

                        $.ajax({
                            url: '/api/products',
                            type: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify(newProduct),
                            success: function() {
                                window.location.href = '/admin/products';
                            },
                            error: function() {
                                alert("Error adding product.");
                            }
                        });
                    } else {
                        alert("All fields are required!");
                    }
                },
                "Cancel": function() {
                    $(this).dialog("close");
                }
            }
        });
    });

    $(".edit-btn").click(function() {
        let row = $(this).closest("tr");
        row.find(".display-text").hide();
        row.find(".edit-input").show();
        $(this).hide();
        row.find(".save-btn").show();
    });

    $(document).on("click", ".save-btn", function() {
        let row = $(this).closest("tr");
        let id = row.data("id");
        let name = row.find(".edit-input").eq(0).val();
        let price = row.find(".edit-input").eq(1).val();
        let imageUrl = row.find(".edit-input").eq(2).val();

        let updatedProduct = {
            id: id,
            name: name,
            price: price,
            imageUrl: imageUrl
        };

        $.ajax({
            url: '/api/products/' + id,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(updatedProduct),
            success: function() {
                window.location.href = '/admin/products';
            },
            error: function() {
                alert("Error updating product.");
            }
        });
    });


    $(document).on("click", ".delete-btn", function() {
        let row = $(this).closest("tr");
        let id = row.data("id");

        $.ajax({
            url: '/api/products/' + id,
            type: 'DELETE',
            success: function() {
                window.location.href = '/admin/products';
            },
            error: function() {
                alert("Error deleting product.");
            }
        });
    });
});
