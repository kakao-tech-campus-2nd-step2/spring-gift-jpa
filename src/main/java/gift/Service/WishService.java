package gift.Service;

import java.util.List;

import gift.DAO.WishDAO;
import gift.DTO.WishRequest;
import gift.Entity.WishEntity;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishDAO wishDAO;

    public WishService(WishDAO wishDAO) {
        this.wishDAO = wishDAO;
    }

    public void addWish(Long userId, WishRequest request) {
        WishEntity wish = new WishEntity();
        wish.setUserId(userId);
        wish.setProductId(request.getProductId());
        wishDAO.save(wish);
    }

    public List<WishEntity> getWishes(Long userId) {
        return wishDAO.findByUserId(userId);
    }

    public void removeWish(Long userId, Long wishId) {
        wishDAO.deleteByUserIdAndWishId(userId, wishId);
    }
}