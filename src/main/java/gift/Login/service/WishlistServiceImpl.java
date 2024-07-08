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
        wishlistRepository.addProductToWishlist(memberId, product);
    }

    @Override
    public Wishlist getWishlistByMemberId(UUID memberId) {
        Wishlist wishlist = wishlistRepository.findWishlistByMemberId(memberId);
        List<Product> products = wishlist.getProducts();
        return new Wishlist(memberId, products);
    }

    @Override
    public void updateProductInWishlist(UUID memberId, Long productId, Product product) {
        Wishlist wishlist = wishlistRepository.findWishlistByMemberId(memberId);
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
        wishlistRepository.updateProductInWishlist(memberId, productId, existingProduct);
    }

    @Override
    public void removeProductFromWishlist(UUID memberId, Long productId) {
        wishlistRepository.removeProductFromWishlist(memberId, productId);
    }
}
