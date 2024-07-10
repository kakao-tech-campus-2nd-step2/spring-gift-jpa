package gift.service;

import gift.DTO.Product;
import gift.repository.WishlistRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductService productService;

    @Autowired
    public WishlistService(WishlistRepository wr, ProductService ps) {
        wishlistRepository = wr;
        productService = ps;
    }

    public List<Product> getWishlistByEmail(String email) {
        // 1. 사용자 이메일을 기반으로 Wishlist 레파지토리에서 productId 리스트를 가져온다
        // 2. 리스트에 들어있는 id들을 Product 레파지토리에서 검색하여 상품 목록 리턴
        List<Long> productIds = wishlistRepository.findProductIdsByUserEmail(email);
        return productService.getAllProductsByIds(productIds);
    }

    public void addWishlist(String email, Long productId) {
        // 사용자 이메일과 제품 ID를 사용하여 위시리스트에 추가
        wishlistRepository.addProductToWishlist(email, productId);
    }

    public void removeWishlist(String email, Long productId) {
        wishlistRepository.removeProductFromWishlist(email, productId);
    }
}
