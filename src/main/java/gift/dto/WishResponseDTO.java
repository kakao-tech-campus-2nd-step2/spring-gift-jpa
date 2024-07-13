package gift.dto;

import gift.entity.ProductInfo;

import java.util.List;

public class WishResponseDTO{

    private String email;
    private List<ProductInfo> Products;


    public WishResponseDTO(String email, List<ProductInfo> products) {
        this.email = email;
        Products = products;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ProductInfo> getProducts() {
        return Products;
    }

    public void setProducts(List<ProductInfo> products) {
        Products = products;
    }
}
