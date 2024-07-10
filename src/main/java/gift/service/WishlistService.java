package gift.service;

import gift.dto.request.WishlistNameRequest;
import gift.domain.WishlistItem;
import gift.exception.MemberNotFoundException;
import gift.repository.wishlist.WishlistRepository;
import gift.repository.wishlist.WishlistSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistSpringDataJpaRepository wishlistRepository;
    private final TokenService tokenService;

    @Autowired
    public WishlistService(WishlistSpringDataJpaRepository wishlistRepository, TokenService tokenService) {
        this.wishlistRepository = wishlistRepository;
        this.tokenService = tokenService;
    }

    public void addItemToWishlist(WishlistNameRequest wishlistNameRequest, String token) {
        String memberId = tokenService.getMemberIdFromToken(token);
        WishlistItem item = new WishlistItem(Long.parseLong(memberId), wishlistNameRequest.getProductId());
        wishlistRepository.save(item);
    }

    public void deleteItemFromWishlist(Long productId, String token) {
        String memberId = tokenService.getMemberIdFromToken(token);
        boolean itemExists = wishlistRepository.findByMemberId(Long.parseLong(memberId))
                .stream()
                .anyMatch(item -> item.getProductId().equals(productId));

        if (!itemExists) {
            throw new MemberNotFoundException("해당 아이템이 존재하지 않습니다: " + productId);
        }

        wishlistRepository.deleteById(productId);
    }

    public List<WishlistItem> getWishlistByMemberId(Long memberId) {
        return wishlistRepository.findByMemberId(memberId);
    }
}
