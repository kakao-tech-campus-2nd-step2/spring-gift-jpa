package gift.service;

import gift.exception.CustomException.ItemNotFoundException;
import gift.exception.CustomException.UserNotFoundException;
import gift.exception.ErrorCode;
import gift.model.item.Item;
import gift.model.user.User;
import gift.model.wishList.WishItem;
import gift.model.wishList.WishListResponse;
import gift.repository.ItemRepository;
import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class WishListService {

    private final ItemRepository itemRepository;
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;

    public WishListService(
        ItemRepository itemRepository, WishListRepository wishListRepository,
        UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
    }
    @Transactional(readOnly = true)
    public List<WishListResponse> getWishList(Long userId) {
        List<WishItem> list = wishListRepository.findAllByUserId(userId);
        return list.stream()
            .map(o -> new WishListResponse(o.getId(), o.getItem().toItemDTO()))
            .collect(Collectors.toList());
    }
    @Transactional
    public Long addToWishList(Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(ErrorCode.ITEM_NOT_FOUND));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        WishItem wishItem = new WishItem(0L, user, item);
        return wishListRepository.save(wishItem).getId();
    }
    @Transactional
    public void deleteFromWishList(Long id) {
        wishListRepository.deleteById(id);
    }
}