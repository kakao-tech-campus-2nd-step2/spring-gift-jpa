package gift.service;

import gift.domain.Product;
import gift.domain.User;
import gift.domain.Wish;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.exception.UserNotFoundException;
import gift.exception.WishNotFoundException;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;

    private final String NOT_FOUND_USER_BY_EMAIL_MESSAGE = "해당 email을 가진 user가 존재하지 않습니다";
    private final String NOT_FOUND_WISH_MESSAGE = "해당 wish가 존재하지 않습니다.";

    public WishService(WishRepository wishRepository, UserRepository userRepository) {
        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
    }

    public void addWish(String userEmail, WishRequestDto wishRequestDto) {
        User user = userRepository.findByEmail(userEmail).get();
        wishRepository.addWish(
            new Wish(user.getId(), wishRequestDto.getProductId(), wishRequestDto.getQuantity()));
    }

    public List<WishResponseDto> findByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_BY_EMAIL_MESSAGE));

        List<Wish> wishes = wishRepository.findById(user.getId())
            .orElseThrow(() -> new WishNotFoundException(NOT_FOUND_WISH_MESSAGE));

        return wishes.stream()
            .map(this::convertToWishDto)
            .collect(Collectors.toList());
    }

    private WishResponseDto convertToWishDto(Wish wish) {
        return new WishResponseDto(
            wish.getId(),
            wish.getUserId(),
            wish.getProductId(),
            wish.getQuantity()
        );
    }

    public void deleteWish(String userEmail, Long productId) {
        User user = userRepository.findByEmail(userEmail).get();
        wishRepository.deleteWish(user.getId(), productId);
    }

    public void updateWish(String userEmail, Long productId, int quantity) {
        User user = userRepository.findByEmail(userEmail).get();
        wishRepository.updateWish(user.getId(), productId, quantity);
    }
}
