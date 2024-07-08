package gift.wishlist.application;

import gift.error.WishAlreadyExistsException;
import gift.product.dao.ProductRepository;
import gift.product.entity.Product;
import gift.wishlist.dao.WishesRepository;
import gift.wishlist.entity.Wish;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WishesService {

    private final WishesRepository wishesRepository;
    private final ProductRepository productRepository;

    public WishesService(WishesRepository wishesRepository, ProductRepository productRepository) {
        this.wishesRepository = wishesRepository;
        this.productRepository = productRepository;
    }

    public void addProductToWishlist(Wish wish) {
        productRepository.findById(wish.getProductId())
                .orElseThrow(() -> new NoSuchElementException("해당 상품은 존재하지 않습니다."));
        if (wishesRepository.exists(wish.getMemberId(), wish.getProductId())) {
            throw new WishAlreadyExistsException();
        }

        wishesRepository.save(wish);
    }

    public void removeProductFromWishlist(Wish wish) {
        if (!wishesRepository.exists(wish.getMemberId(), wish.getProductId())) {
            throw new NoSuchElementException("해당 상품은 위시 리스트에 존재하지 않습니다.");
        }

        wishesRepository.delete(wish);
    }

    public List<Product> getWishlistOfMember(Long memberId) {
        List<String> productIdList = wishesRepository.findByMemberId(memberId);

        return productRepository.findById(productIdList);
    }

}
