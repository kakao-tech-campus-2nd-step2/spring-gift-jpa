package gift.Login.service;

import gift.Login.model.Product;
import gift.Login.model.Wishlist;
import gift.Login.repository.ProductRepository;
import gift.Login.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void addProductToWishlist(Long memberId, Product product) {
        Wishlist wishlist = wishlistRepository.findByMemberId(memberId)
                .orElseGet(() -> new Wishlist(memberId));

        // 먼저 Product를 저장합니다.
        productRepository.save(product);

        // 저장된 Product를 Wishlist에 추가합니다.
        wishlist.getProducts().add(product);
        product.setWishlist(wishlist);

        // Wishlist를 저장합니다.
        wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional(readOnly = true)
    public Wishlist getWishlistByMemberId(Long memberId) {
        return wishlistRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found for memberId: " + memberId));
    }

    @Override
    @Transactional
    public void updateProductInWishlist(Long memberId, Long productId, Product updatedProduct) {
        Wishlist wishlist = wishlistRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found for memberId: " + memberId));
        Product product = wishlist.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in wishlist: " + productId));

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setTemperatureOption(updatedProduct.getTemperatureOption());
        product.setCupOption(updatedProduct.getCupOption());
        product.setSizeOption(updatedProduct.getSizeOption());
        product.setImageUrl(updatedProduct.getImageUrl());

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void removeProductFromWishlist(Long memberId, Long productId) {
        Wishlist wishlist = wishlistRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found for memberId: " + memberId));
        Product product = wishlist.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in wishlist: " + productId));

        wishlist.getProducts().remove(product);
        product.setWishlist(null);
        productRepository.delete(product);
    }
}
