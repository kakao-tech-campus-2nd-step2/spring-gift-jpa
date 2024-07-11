package gift.service;

import gift.domain.Product;
import gift.domain.Wishlist;
import gift.domain.member.Member;
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

    public List<ProductResponse> getProducts(Member member) {
        List<Wishlist> wishes = member.getWishes();

        return wishes.stream()
                .map(Wishlist::getProduct)
                .map(Product::toDto)
                .collect(Collectors.toList());
    }

    public void addProduct(Member member, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        if (wishlistRepository.existsByMemberIdAndProductId(member.getId(), productId)) {
            throw new ProductAlreadyInWishlistException();
        }

        Wishlist wishlist = new Wishlist(member, product);

        wishlistRepository.save(wishlist);
    }

    public void removeProduct(Member member, Long productId) {
        if (!wishlistRepository.existsByMemberIdAndProductId(member.getId(), productId)) {
            throw new ProductNotInWishlistException();
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        Wishlist wishlist = new Wishlist(member, product);

        wishlistRepository.delete(wishlist);
    }
}
