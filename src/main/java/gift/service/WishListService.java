package gift.service;

import gift.dao.WishDao;
import gift.dto.WishDto;
import gift.model.wish.Wish;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WishListService {
    private final WishDao wishDao;

    public WishListService(WishDao wishDao){
        this.wishDao = wishDao;
    }

    public List<Wish> getAllWishes() {
        return wishDao.getAllWishes();
    }

    public void insertWish(WishDto wishDto) {
        Wish wish = new Wish(wishDto.getProductId(),wishDto.getProductName(), wishDto.getAmount());
        wishDao.insertWish(wish);
    }

    public void deleteWish(Long productId) {
        wishDao.deleteWish(productId);
    }

    public void updateWish(WishDto wishDto){
        Wish wish = new Wish(wishDto.getProductId(),wishDto.getProductName(), wishDto.getAmount());
        wishDao.updateWish(wish);
    }
}
