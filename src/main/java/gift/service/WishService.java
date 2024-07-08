package gift.service;

import gift.controller.dto.WishRequestDTO;
import gift.domain.UserInfo;
import gift.domain.Wish;
import gift.repository.ProductRepository;
import gift.repository.UserInfoRepository;
import gift.repository.WishsRepository;
import gift.utils.error.ProductNotFoundException;
import gift.utils.error.UserNotFoundException;
import gift.utils.error.WishListAddFailedException;
import gift.utils.error.WishListNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishsRepository wishsRepository;
    private final ProductRepository productRepository;
    private final UserInfoRepository userInfoRepository;

    public WishService(WishsRepository wishsRepository, ProductRepository productRepository,
        UserInfoRepository userInfoRepository) {
        this.wishsRepository = wishsRepository;
        this.productRepository = productRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public boolean addToWishlist(String email, WishRequestDTO wishRequestDTO) {
        UserInfo byEmail = userInfoRepository.findByEmail(email).orElseThrow(
            ()-> new UserNotFoundException("User Not Found"));
        Wish wish = new Wish( wishRequestDTO.getProductId(),byEmail.getId(),wishRequestDTO.getQuantity());
        try {
            wishsRepository.save(wish);
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
            wishsRepository.deleteByProductIdAndUserId(productId,userInfo.getId());
            return true;
        } catch (Exception e) {
            throw new WishListNotFoundException("Not Found");
        }

    }

    public List<Wish> getWishlistProducts(String email) {
        UserInfo userInfo = userInfoRepository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException("User Not Found")
        );
        return wishsRepository.findByUserId(userInfo.getId());
    }
    public boolean changeToWishlist(String email, WishRequestDTO wishRequestDTO) {
        UserInfo userInfo = userInfoRepository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException("User Not Found")
        );
        Wish wish = new Wish(userInfo.getId(), wishRequestDTO.getProductId(),wishRequestDTO.getQuantity());
        try {
            if (wishRequestDTO.getQuantity()==0){
                wishsRepository.deleteByProductIdAndUserId(wishRequestDTO.getProductId(),userInfo.getId());
                return true;
            }
            if (!wishsRepository.existsByUserIdAndProductId(userInfo.getId(),wishRequestDTO.getProductId())){
                throw new ProductNotFoundException("Product Not Found");
            }
            Wish byUserIdAndProductId = wishsRepository.findByUserIdAndProductId(wish.userId(),
                wish.getProductId());
            byUserIdAndProductId.setQuantity(wish.getQuantity());
            return true;
        } catch (Exception e) {
            throw new WishListAddFailedException("Change Failed");
        }

    }


}
