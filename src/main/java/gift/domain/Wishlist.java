package gift.domain;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class Wishlist {

    @NotNull
    private String userEmail;
    @NotNull
    private ProductWithQuantity productWithQuantity;

    public Wishlist(String userEmail, ProductWithQuantity productWithQuantity){
        this.userEmail=userEmail;
        this.productWithQuantity=productWithQuantity;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public ProductWithQuantity getProductWithQuantity(){
        return productWithQuantity;
    }

}
