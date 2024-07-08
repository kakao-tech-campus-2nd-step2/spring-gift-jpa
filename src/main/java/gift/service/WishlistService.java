// WishlistService.java
package gift.service;

import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public Wish addProduct(Wish product) {
        return wishlistRepository.addOrUpdateProduct(product);
    }

    public List<Wish> getProductsByMemberId(Long memberId) {
        return wishlistRepository.getProductsByMemberId(memberId);
    }

    public void deleteItem(Long wishId) {
        if (!wishlistRepository.existsById(wishId)) {
            throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");
        }
        wishlistRepository.deleteItem(wishId);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public void updateProductNumber(Long id, int productNumber) {
        if (!wishlistRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");
        }
        wishlistRepository.updateProductNumber(id, productNumber);
    }
}
