package gift.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WishListDTO implements Serializable {

    private Long id;
    private UserDTO user;
    private List<ProductDTO> products = new ArrayList<>();

    public WishListDTO() {}

    public WishListDTO(Long id, UserDTO user, List<ProductDTO> products) {
        this.id = id;
        this.user = user;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}