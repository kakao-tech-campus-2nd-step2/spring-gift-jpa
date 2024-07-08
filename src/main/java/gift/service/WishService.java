package gift.service;

import gift.controller.dto.WishRequestDTO;
import gift.domain.Wish;
import gift.repository.ProductJDBCRepository;
import gift.repository.WishsRepository;
import gift.utils.error.ProductNotFoundException;
import gift.utils.error.WishListAddFailedException;
import gift.utils.error.WishListNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishsRepository wishsRepository;
    private final ProductJDBCRepository productJDBCRepository;

    public WishService(WishsRepository wishsRepository, ProductJDBCRepository productJDBCRepository) {
        this.wishsRepository = wishsRepository;
        this.productJDBCRepository = productJDBCRepository;
    }

    public boolean addToWishlist(String email, WishRequestDTO wishRequestDTO) {
        Wish wish = new Wish(email, wishRequestDTO.getProductId(),wishRequestDTO.getQuantity());
        try {
            return wishsRepository.addToWishlist(wish);
        } catch (Exception e) {
            throw new WishListAddFailedException("Add Failed");
        }

    }

    public boolean removeFromWishlist(String email, Long productId) {
        try {
            return wishsRepository.removeFromWishlist(email, productId);
        } catch (Exception e) {
            throw new WishListNotFoundException("Not Found");
        }

    }

    public List<Wish> getWishlistProducts(String email) {
        return wishsRepository.getWishlistProducts(email);
    }
    public boolean changeToWishlist(String email, WishRequestDTO wishRequestDTO) {
        Wish wish = new Wish(email, wishRequestDTO.getProductId(),wishRequestDTO.getQuantity());
        try {
            if (wishRequestDTO.getQuantity()==0){
                return wishsRepository.removeFromWishlist(email,wishRequestDTO.getProductId());
            }
            if (productJDBCRepository.checkexist(wishRequestDTO.getProductId())){
                throw new ProductNotFoundException("Product Not Found");
            }
            return wishsRepository.changeToWishlist(wish);
        } catch (Exception e) {
            throw new WishListAddFailedException("Change Failed");
        }

    }


}
