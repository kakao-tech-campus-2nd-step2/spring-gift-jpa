package gift.service;

import gift.domain.Product;
import gift.domain.Wishlist;
import gift.exception.ProductAlreadyInWishlistException;
import gift.exception.ProductNotFoundException;
import gift.exception.ProductNotInWishlistException;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import gift.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository,
                           ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getProducts(Long memberId) {
        List<Wishlist> wishlist = wishlistRepository.findByMemberId(memberId);

        List<Long> productIds = wishlist.stream()
                .map(Wishlist::getProductId)
                .collect(Collectors.toList());

        return productRepository.findByIdIn(productIds).stream()
                .map(Product::toDto)
                .collect(Collectors.toList());
    }

    public void addProduct(Long memberId, Long productId) {
        productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        if (wishlistRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new ProductAlreadyInWishlistException();
        }

        Wishlist wishlist = new Wishlist(memberId, productId);

        wishlistRepository.save(wishlist);
    }

    public void removeProduct(Long memberId, Long productId) {
        if (!wishlistRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new ProductNotInWishlistException();
        }

        Wishlist wishlist = new Wishlist(memberId, productId);

        wishlistRepository.delete(wishlist);
    }
}
