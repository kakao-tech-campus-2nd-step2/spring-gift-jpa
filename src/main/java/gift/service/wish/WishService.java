package gift.service.wish;

import gift.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WishService {

    // 실제 데이터베이스를 사용하지 않고, 임시로 HashMap을 사용하여 위시리스트를 저장합니다.
    private final Map<User, List<String>> userWishlistMap = new HashMap<>();

    public List<String> getWishlistByUser(User user) {
        return userWishlistMap.getOrDefault(user, new ArrayList<>());
    }

    public void addToWishlist(User user, String product) {
        List<String> wishlist = userWishlistMap.computeIfAbsent(user, k -> new ArrayList<>());
        wishlist.add(product);
    }

    public void removeFromWishlist(User user, String product) {
        List<String> wishlist = userWishlistMap.getOrDefault(user, new ArrayList<>());
        wishlist.remove(product);
    }
}
