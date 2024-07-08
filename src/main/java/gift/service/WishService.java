package gift.service;

import gift.dto.WishlistResponseDTO;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public void addWishProduct(Long userId, Long productId) {
        wishRepository.insertWishProduct(userId, productId);
    }

    public WishlistResponseDTO getWishlist(Long userId) {
        return new WishlistResponseDTO(userId, wishRepository.selectWishlist(userId));
    }

    public void deleteWishProduct(Long userId, Long productId) {
        wishRepository.deleteWishProduct(userId, productId);
    }
}
