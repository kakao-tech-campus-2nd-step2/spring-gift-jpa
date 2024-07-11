package gift.service;

import gift.model.Gift;
import gift.model.User;
import gift.model.Wish;
import gift.repository.GiftRepository;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private WishRepository wishRepository;
    private UserRepository userRepository;
    private GiftRepository giftRepository;

    @Autowired
    public WishService(WishRepository wishRepository, UserRepository userRepository, GiftRepository giftRepository) {
        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
        this.giftRepository = giftRepository;
    }

    public void addGiftToUser(Long userId, Long giftId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Gift gift = giftRepository.findById(giftId).orElseThrow(() -> new IllegalArgumentException("Invalid gift ID"));

        List<Wish> existingWishes = wishRepository.findByUserAndGift(user, gift);
        if (!existingWishes.isEmpty()) {
            Wish existingWish = existingWishes.get(0);
            existingWish.increaseQuantity();
            wishRepository.save(existingWish);
        } else {
            Wish userGift = new Wish(user, gift, quantity);
            wishRepository.save(userGift);
        }
    }

    public void removeGiftFromUser(Long userId, Long giftId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Gift gift = giftRepository.findById(giftId).orElseThrow(() -> new IllegalArgumentException("Invalid gift ID"));
        wishRepository.deleteByUserAndGift(user, gift);
    }

    public List<Wish> getGiftsForUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return wishRepository.findByUser(user, pageable);
    }
}