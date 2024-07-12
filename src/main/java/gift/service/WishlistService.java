package gift.service;

import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public Wish addProduct(Wish wish) {
        if (wish.getProduct() == null || wish.getProduct().getId() == 0) {
            throw new IllegalArgumentException("Product must not be null");
        }

        Optional<Wish> existingWish = wishlistRepository.findByMemberIdAndProductId(wish.getMember().getId(), wish.getProduct().getId());
        if (existingWish.isPresent()) {
            Wish foundWish = existingWish.get();
            foundWish.setProductNumber(foundWish.getProductNumber() + wish.getProductNumber());
            return wishlistRepository.save(foundWish);
        } else {
            return wishlistRepository.save(wish);
        }
    }

    public List<Wish> getWishesByMemberId(Long memberId) {
        return wishlistRepository.findByMemberId(memberId);
    }

    public void deleteItem(Long wishId) {
        if (!wishlistRepository.existsById(wishId)) {
            throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");
        }
        wishlistRepository.deleteById(wishId);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public void updateProductNumber(Long id, int productNumber) {
        Wish wish = wishlistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        wish.setProductNumber(productNumber);
        wishlistRepository.save(wish);
    }
}
