package gift.Login.service;

import gift.Login.model.Product;
import gift.Login.model.Wishlist;
import gift.Login.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public void addProductToWishlist(UUID memberId, Product product) {
        Wishlist wishlist = wishlistRepository.findByMemberId(memberId);
        if (wishlist == null) {
            wishlist = new Wishlist(memberId, List.of(product));
        } else {
            wishlist.getProducts().add(product);
        }
        wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist getWishlistByMemberId(UUID memberId) {
        return wishlistRepository.findByMemberId(memberId);
    }

    @Override
    public void updateProductInWishlist(UUID memberId, Long productId, Product product) {
        Wishlist wishlist = wishlistRepository.findByMemberId(memberId);
        if (wishlist == null) {
            throw new RuntimeException("Wishlist not found");
        }

        Product existingProduct = wishlist.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingProduct == null) {
            throw new RuntimeException("Product not found in wishlist");
        }

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setTemperatureOption(product.getTemperatureOption());
        existingProduct.setCupOption(product.getCupOption());
        existingProduct.setSizeOption(product.getSizeOption());
        existingProduct.setImageUrl(product.getImageUrl());
        wishlistRepository.save(wishlist);
    }

    @Override
    public void removeProductFromWishlist(UUID memberId, Long productId) {
        Wishlist wishlist = wishlistRepository.findByMemberId(memberId);
        if (wishlist != null) {
            wishlist.getProducts().removeIf(product -> product.getId().equals(productId));
            wishlistRepository.save(wishlist);
        }
    }
}
