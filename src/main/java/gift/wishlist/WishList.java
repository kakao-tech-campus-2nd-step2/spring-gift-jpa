package gift.wishlist;

import gift.product.Product;

import java.util.ArrayList;
import java.util.List;

public class WishList {

    // final 추가
    private final Long id;
    private final Long userId;
    private final String name;
    private List<Product> products = new ArrayList<>();

    public WishList(Long id, Long userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.products = new ArrayList<>();
    }

    // ID 반환 메서드
    public Long getId() {
        return id;
    }

    // User ID 반환 메서드
    public Long getUserId() {
        return userId;
    }

    // 이름 반환 메서드
    public String getName() {
        return name;
    }

    // 불변의 제품 리스트 반환 메서드
    public List<Product> getProducts() {
        return products;
    }

    // Products 리스트를 조작하는 메서드
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}