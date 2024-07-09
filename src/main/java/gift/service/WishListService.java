package gift.service;

import gift.dto.response.WishProductResponse;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.ProductNotFoundException;
import gift.exception.WishAlreadyExistsException;
import gift.exception.WishNotFoundException;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private final WishRepository wishListRepository;
    private final ProductRepository productRepository;

    public WishListService(WishRepository wishListRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
    }

    public void addProductToWishList(Long memberId, Long productId, int amount) {
        wishListRepository.findByMemberIdAndProductId(memberId, productId)
                .ifPresent(duplicatedWish -> {
                    throw new WishAlreadyExistsException("Product already exist in your wishlist");
                });
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Wish wish = new Wish(memberId, amount, product);
        wishListRepository.save(wish);
    }

    public void deleteProductInWishList(Long memberId, Long productId) {
        Wish wish = wishListRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new WishNotFoundException("Wish not found"));
        wishListRepository.delete(wish);
    }

    public void updateWishProductAmount(Long memberId, Long productId, int amount) {
        Wish wish = wishListRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() -> new WishNotFoundException("Wish not found"));
        wish.setAmount(amount);

        wishListRepository.save(wish);
    }

    public List<WishProductResponse> getWishProductsByMemberId(Long memberId) {
        return wishListRepository.findAllByMemberIdWithProduct(memberId)
                .stream()
                .map(wish -> new WishProductResponse(wish.getProduct().getId(), wish.getProduct().getName(), wish.getProduct().getPrice(), wish.getProduct().getImageUrl(), wish.getAmount()))
                .toList();
    }

}
