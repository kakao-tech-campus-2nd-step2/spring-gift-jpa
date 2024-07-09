package gift.service;

import gift.exception.CustomException.ItemNotFoundException;
import gift.exception.ErrorCode;
import gift.model.item.Item;
import gift.model.wishList.WishItem;
import gift.model.wishList.WishItemDto;
import gift.repository.ItemRepository;
import gift.repository.WishListRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final ItemRepository itemRepository;
    private final WishListRepository wishListRepository;

    public WishListService(
        ItemRepository itemRepository, WishListRepository wishListRepository) {
        this.itemRepository = itemRepository;
        this.wishListRepository = wishListRepository;
    }

    public List<WishItemDto> getList(Long userId) {
        List<WishItem> list = wishListRepository.findAllByUserId(userId);
        return list.stream()
            .map(item -> new WishItemDto(item.getId(), item.getUserId(), item.getItemId()))
            .collect(Collectors.toList());
    }

    public void addToWishList(Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(ErrorCode.ITEM_NOT_FOUND));
        WishItem wishItem = new WishItem(0L, userId, item.getId());
        wishListRepository.save(wishItem);
    }

    public void deleteFromWishList(Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(ErrorCode.ITEM_NOT_FOUND));
        wishListRepository.deleteByUserIdAndItemId(userId, itemId);
    }
}