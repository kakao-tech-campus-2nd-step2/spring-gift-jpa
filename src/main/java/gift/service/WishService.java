package gift.service;

import gift.controller.dto.WishRequestDTO;
import gift.domain.Product;
import gift.domain.UserInfo;
import gift.domain.Wish;
import gift.repository.ProductRepository;
import gift.repository.UserInfoRepository;
import gift.repository.WishRepository;
import gift.utils.error.ProductNotFoundException;
import gift.utils.error.UserNotFoundException;
import gift.utils.error.WishListAddFailedException;
import gift.utils.error.WishListNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final UserInfoRepository userInfoRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, UserInfoRepository userInfoRepository,
        ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.userInfoRepository = userInfoRepository;
        this.productRepository = productRepository;
    }


    public boolean addToWishlist(String email, WishRequestDTO wishRequestDTO) {
        Product product = productRepository.findById(wishRequestDTO.getProductId()).orElseThrow(
            () -> new ProductNotFoundException("Product Not Found")
        );
        UserInfo byEmail = userInfoRepository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException("User Not Found"));
        Wish wish = new Wish(product, byEmail, wishRequestDTO.getQuantity());
        try {
            wishRepository.save(wish);
            return true;
        } catch (Exception e) {
            throw new WishListAddFailedException("Add Failed");
        }

    }

    public boolean removeFromWishlist(String email, Long productId) {
        UserInfo userInfo = userInfoRepository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException("User Not Found")
        );
        try {
            wishRepository.deleteByProduct_IdAndUserInfo_Id(productId, userInfo.getId());
            return true;
        } catch (Exception e) {
            throw new WishListNotFoundException("Not Found");
        }

    }

    public List<Wish> getWishlistProducts(String email) {
        UserInfo userInfo = userInfoRepository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException("User Not Found")
        );
        return wishRepository.findByUserInfo_Id(userInfo.getId());
    }

    public boolean changeToWishlist(String email, WishRequestDTO wishRequestDTO) {
        Product product = productRepository.findById(wishRequestDTO.getProductId()).orElseThrow(
            () -> new ProductNotFoundException("Product Not Found")
        );
        UserInfo userInfo = userInfoRepository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException("User Not Found")
        );
        Wish wish = new Wish(product, userInfo, wishRequestDTO.getQuantity());
        try {
            if (wishRequestDTO.getQuantity() == 0) {
                wishRepository.deleteByProduct_IdAndUserInfo_Id(wishRequestDTO.getProductId(),
                    userInfo.getId());
                return true;
            }
            if (!wishRepository.existsByUserInfo_IdAndProduct_Id(userInfo.getId(),
                wishRequestDTO.getProductId())) {
                throw new ProductNotFoundException("Product Not Found");
            }
            Wish byUserIdAndProductId = wishRepository.findByUserInfo_IdAndProduct_Id(
                wish.getProduct().getId(),
                wish.getUserInfo().getId());
            byUserIdAndProductId.setQuantity(wish.getQuantity());
            return true;
        } catch (Exception e) {
            throw new WishListAddFailedException("Change Failed");
        }

    }


}
