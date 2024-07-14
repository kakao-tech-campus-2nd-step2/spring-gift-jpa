package gift.service;

import gift.model.WishlistItem;
import gift.repository.WishlistRepository;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
=======
>>>>>>> 0efc70c (경북대 BE_김동윤 3주차 과제 (0, 1, 2단계) (#66))
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
<<<<<<< HEAD
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
<<<<<<< HEAD

=======
    public List<WishlistItem> saveWishlistItems(List<WishlistItem> wishlistItems) {
        return wishlistRepository.saveAll(wishlistItems);
    }
>>>>>>> 0efc70c (경북대 BE_김동윤 3주차 과제 (0, 1, 2단계) (#66))
=======
>>>>>>> 389ddd10b29ca52b35afd37068ac02ab8772a9cc
}
