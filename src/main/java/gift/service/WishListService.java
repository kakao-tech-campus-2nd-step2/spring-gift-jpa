package gift.service;

import gift.model.WishListItem;
import gift.repository.WishListRepository;
import gift.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final JwtUtil jwtUtil;

    public WishListService(WishListRepository wishListRepository, JwtUtil jwtUtil) {
        this.wishListRepository = wishListRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<WishListItem> getWishListByToken(String token) {
        Long memberId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
        return wishListRepository.findWishListByMemberId(memberId);
    }

    public void addWishListItem(String token, WishListItem item) {
        Long memberId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
        item.setMemberId(memberId);
        wishListRepository.addWishListItem(item);
    }

    public void deleteWishListItem(String token, Long id) {
        Long memberId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
        WishListItem item = wishListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        if (!item.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("Not authorized to delete this item");
        }
        wishListRepository.removeWishListItem(id);
    }
}
