package gift.service;

import gift.model.UserGift;
import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private WishRepository wishRepository;

    @Autowired
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public void addGiftToUser(Long userId, Long giftId, int quantity) {
        UserGift userGift = new UserGift(userId,giftId,quantity);
        wishRepository.save(userGift);
    }

    public void removeGiftFromUser(Long userId, Long giftId) {
        wishRepository.deleteByUserIdAndGiftId(userId,giftId);
    }

    public List<UserGift> getGiftsForUser(Long userId) {
        return wishRepository.findByUserId(userId);
    }
}
