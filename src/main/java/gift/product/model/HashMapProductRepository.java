package gift.product.model;

import gift.product.model.dto.ProductResponse;
import gift.product.model.dto.UpdateProductRequest;
import gift.product.model.dto.CreateProductRequest;
import gift.product.model.dto.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapProductRepository {
    /*
     원래는 Product 객체와 같은 디렉토리 안에 있었습니다 (protected 접근 가능)
     step1 과제는 이를 생각하고 Product 객체에 getter/setter 없이 코드를 작성했습니다.
     step3 과제를 하면서 Product 객체를 DTO 패키지로 분리하면서 getter/setter를 추가했습니다.
     */
    private final Map<Long, Product> products = new HashMap<>();
    private Long nextId = 1L;

    public Long addProduct(CreateProductRequest newProduct) {
        Long id = nextId++;
        Product product = new Product(id, newProduct.getName(), newProduct.getPrice(), newProduct.getImageUrl(), true);
        products.put(id, product);
        return id;
    }

    public ProductResponse findProduct(Long id) {
        Product product = validateProductId(id);
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public List<ProductResponse> findAllProduct() {
        return products.values().stream()
                .filter(product -> product.isActive())
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice(),
                        product.getImageUrl()))
                .toList();
    }

    public void updateProduct(UpdateProductRequest updatedProduct) {
        Product product = validateProductId(updatedProduct.getId());
        if (updatedProduct.getName() != null) {
            product.setName(updatedProduct.getName());
        }
        if (updatedProduct.getPrice() != 0) {
            product.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getImageUrl() != null) {
            product.setImageUrl(updatedProduct.getImageUrl());
        }
    }

    public void deleteProduct(Long id) {
        Product product = validateProductId(id);
        product.setActive(false);
    }

    // 상품 ID 유효성 검증 메서드
    private Product validateProductId(Long id){
        if (products.containsKey(id)) {
            Product product = products.get(id);
            if (product.isActive()) {
                return product;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 상품 ID 입니다.");
    }
}
