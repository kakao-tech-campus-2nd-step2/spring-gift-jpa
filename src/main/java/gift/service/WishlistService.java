package gift.service;

import gift.dto.request.WishlistNameRequest;
import gift.domain.WishlistItem;
import gift.exception.MemberNotFoundException;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final TokenService tokenService;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository, TokenService tokenService) {
        this.wishlistRepository = wishlistRepository;
        this.tokenService = tokenService;
    }

    public void addItemToWishlist(WishlistNameRequest wishlistNameRequest, String token) {
        String memberId = tokenService.getMemberIdFromToken(token);
        WishlistItem item = new WishlistItem(Long.parseLong(memberId), wishlistNameRequest.getItemName());
        wishlistRepository.addItem(item);
    }

    public void deleteItemFromWishlist(Long itemId, String token) {
        String memberId = tokenService.getMemberIdFromToken(token);
        boolean itemExists = wishlistRepository.getItemsByMemberId(Long.parseLong(memberId))
                .stream()
                .anyMatch(item -> item.getId().equals(itemId));

        if (!itemExists) {
            throw new MemberNotFoundException("해당 아이템이 존재하지 않습니다: " + itemId);
        }

        wishlistRepository.deleteItem(itemId);
    }

    public List<WishlistItem> getWishlistByMemberId(Long memberId) {
        return wishlistRepository.getItemsByMemberId(memberId);
    }
}
