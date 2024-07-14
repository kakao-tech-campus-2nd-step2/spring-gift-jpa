package gift.service;

import gift.dto.PagingResponse;
import gift.exception.WishItemNotFoundException;
import gift.model.gift.Gift;
import gift.model.user.User;
import gift.model.wish.Wish;
import gift.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public PagingResponse<Wish> getGiftsForUser(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Page<Wish> wishes = wishRepository.findByUser(user, pageRequest);

        return new PagingResponse<>(page, wishes.getContent(), size, wishes.getTotalElements(), wishes.getTotalPages());
    }

    @Transactional
    public void updateWishQuantity(Long userId, Long giftId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Gift gift = giftRepository.findById(giftId).orElseThrow(() -> new IllegalArgumentException("Invalid gift ID"));
        List<Wish> existingWishes = wishRepository.findByUserAndGift(user, gift);
        if (!existingWishes.isEmpty()) {
            existingWishes.get(0).modifyQuantity(quantity);
            return;
        }
        throw new WishItemNotFoundException("해당 위시리스트 아이템을 찾을 수 없습니다.");
    }
}