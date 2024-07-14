package gift.service;

import gift.model.WishlistItem;
import gift.repository.WishlistRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Page<WishlistItem> getWishlistByUserId(Long userId, Pageable pageable) {
        return wishlistRepository.findListByUserId(userId, pageable);
    }
    public List<WishlistItem> saveWishlistItemsWithUserId(Long userId, List<WishlistItem> wishlistItems) {
        List<WishlistItem> userWishlistItems = wishlistRepository.findListByUserId(userId);
        if(!userWishlistItems.isEmpty()){
            Map<Long, WishlistItem> userWishlistMap = convertListToMap(userWishlistItems);
            userWishlistItems = mergeWishlist(wishlistItems, userWishlistMap);
        }else{
            userWishlistItems = wishlistItems;
        }
        return wishlistRepository.saveAll(userWishlistItems);
    }

    private static Map<Long, WishlistItem> convertListToMap(List<WishlistItem> wishlistItems) {
        Map<Long, WishlistItem> userWishlistMap = new HashMap<>();
        for(WishlistItem wishlistItem : wishlistItems){
            userWishlistMap.put(wishlistItem.getProduct().getId(), wishlistItem);
        }
        return userWishlistMap;
    }

    private static List<WishlistItem> mergeWishlist(List<WishlistItem> wishlistItems,
        Map<Long, WishlistItem> userWishlistMap) {
        for (WishlistItem wishlistItem : wishlistItems) {
            Long productId = wishlistItem.getProduct().getId();
            if (userWishlistMap.containsKey(productId)) {
                WishlistItem existingItem = userWishlistMap.get(productId);
                existingItem.setAmount(existingItem.getAmount() + wishlistItem.getAmount());
                userWishlistMap.put(productId, existingItem);
            } else {
                userWishlistMap.put(productId, wishlistItem);
            }
        }
        return new ArrayList<>(userWishlistMap.values());
    }

}
