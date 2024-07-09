package gift.service;

import gift.dto.UserRequestDTO;
import gift.model.WishList;
import gift.model.Product;
import gift.repository.WishListRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
    }

    // 사용자의 위시 리스트를 조회하는 메서드
    public List<WishList> getWishlist(UserRequestDTO userRequestDTO) {
        return wishListRepository.findByUserEmail(userRequestDTO.getEmail());
    }

    // 위시 리스트에 상품을 추가하는 메서드
    public void addProductToWishlist(UserRequestDTO userRequestDTO, Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("제품을 찾을 수 없습니다."));
        WishList wishList = new WishList();
        wishList.setUserEmail(userRequestDTO.getEmail());
        wishList.setProductId(product.getId());
        wishListRepository.save(wishList);
    }

    // 위시 리스트에서 상품을 삭제하는 메서드
    public void removeProductFromWishlist(Long wishListId) {
        WishList wishList = wishListRepository.findById(wishListId)
            .orElseThrow(() -> new RuntimeException("위시리스트를 찾을 수 없습니다."));
        wishListRepository.deleteById(wishListId);
    }
}
