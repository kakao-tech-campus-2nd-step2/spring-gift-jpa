package gift.service;

import gift.model.UserGift;
import gift.model.WishDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private WishDao wishDao;

    @Autowired
    public WishService(WishDao wishDao) {
        this.wishDao = wishDao;
    }

    public void addGiftToUser(Long userId, Long giftId, int quantity) {
        wishDao.addGiftToUser(userId, giftId, quantity);
    }

    public void removeGiftFromUser(Long userId, Long giftId) {
        wishDao.removeGiftFromUser(userId, giftId);
    }

    public List<UserGift> getGiftsForUser(Long userId) {
        return wishDao.getGiftsForUser(userId);
    }
}
