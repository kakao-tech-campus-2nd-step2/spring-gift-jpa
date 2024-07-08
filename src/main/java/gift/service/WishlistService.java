package gift.service;

import gift.dto.WishlistDTO;
import java.util.List;

public interface WishlistService {
    List<WishlistDTO> getWishlistByUser(String username);
    void addToWishlist(String username, Long productId, int quantity); // 수량 추가
    void removeFromWishlist(Long id); // 삭제 기능 추가
    void updateQuantity(Long id, int quantity); // 수량 변경 기능 추가
}
