package gift.service;

import gift.dto.wish.WishCreateDTO;
import gift.entity.Wish;
import gift.dto.wish.WishRequestDTO;
import gift.dto.wish.WishResponseDTO;
import gift.exception.ForbiddenRequestException;
import gift.exception.NoSuchUserException;
import gift.exception.NoSuchWishException;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final UserRepository userRepository;

    public WishService(WishRepository wishRepository, UserRepository userRepository) {
        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
    }

    public List<WishResponseDTO> getWishes(String email) {
        long userId = userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new)
                .getId();

        return wishRepository.findAllByUser_Id(userId)
                .stream()
                .map(WishResponseDTO::from)
                .toList();
    }

    public WishResponseDTO addWish(String email, WishRequestDTO wishRequestDTO) {
        long userId = userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new)
                .getId();

        Wish wish = wishRepository.save(new Wish(
                userId,
                wishRequestDTO.productId()
        ));

        return WishResponseDTO.from(wish);
    }

    public void deleteWish(String email, long wishId) {
        long userId = userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new)
                .getId();

        long wishOwnerId = wishRepository.findById(wishId)
                .orElseThrow(NoSuchWishException::new)
                .getId();

        if (wishOwnerId != userId) {
            throw new ForbiddenRequestException("user is not owner of wish");
        }

        wishRepository.deleteById(wishId);
    }
}
