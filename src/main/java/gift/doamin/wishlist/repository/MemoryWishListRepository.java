package gift.doamin.wishlist.repository;

import gift.doamin.wishlist.entity.WishList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryWishListRepository implements WishListRepository {

    private final Map<Long, WishList> wishLists = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @Override
    public WishList save(WishList wishList) {
        Long id = sequence.getAndIncrement();
        wishList.setId(id);
        wishLists.put(id, wishList);
        return wishList;
    }

    @Override
    public List<WishList> findByUserId(Long userId) {
        List<WishList> result = new ArrayList<>();

        for (WishList wishList : wishLists.values()) {
            if (wishList.getUserId().equals(userId)) {
                result.add(wishList);
            }
        }

        return result;
    }

    @Override
    public WishList findByUserIdAndProductId(Long userId, Long productId) {
        for (WishList wishList : wishLists.values()) {
            if (wishList.getUserId().equals(userId) && wishList.getProductId().equals(productId)) {
                return wishList;
            }
        }
        return null;
    }

    @Override
    public void update(WishList wishList) {
        wishLists.put(wishList.getId(), wishList);
    }

    @Override
    public void deleteById(Long id) {
        wishLists.remove(id);
    }

    @Override
    public boolean existsByUserIdAndProductId(Long userId, Long productId) {
        for (WishList wishList : wishLists.values()) {
            if (wishList.getUserId().equals(userId) && wishList.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }
}
