package gift.service;

import gift.domain.Product;
import gift.exception.ProductAlreadyInWishlistException;
import gift.exception.ProductNotFoundException;
import gift.exception.ProductNotInWishlistException;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import gift.response.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public List<ProductResponse> getProducts(Long memberId) {
        return wishlistRepository.findAllProducts(memberId).stream()
            .map(Product::toDto)
            .collect(Collectors.toList());
    }

    public void addProduct(Long memberId, Long productId) {
        productRepository.findById(productId)
            .orElseThrow(ProductNotFoundException::new);

        if (wishlistRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new ProductAlreadyInWishlistException();
        }

        wishlistRepository.save(memberId, productId);
    }

    public void removeProduct(Long memberId, Long productId) {
        if (!wishlistRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new ProductNotInWishlistException();
        }

        wishlistRepository.delete(memberId, productId);
    }
}
