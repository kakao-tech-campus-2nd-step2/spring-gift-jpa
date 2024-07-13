package gift.wishlist.service;

import gift.global.dto.PageRequestDto;
import gift.global.utility.SortingStateUtility;
import gift.permission.repository.PermissionRepository;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.user.entity.User;
import gift.wishlist.dto.WishListRequestDto;
import gift.wishlist.dto.WishListResponseDto;
import gift.wishlist.entity.WishList;
import gift.wishlist.model.WishListId;
import gift.wishlist.repository.WishListRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final PermissionRepository permissionRepository;

    public WishListService(WishListRepository wishListRepository,
        ProductRepository productRepository, PermissionRepository permissionRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.permissionRepository = permissionRepository;
    }

    // 위시리스트 추가하는 핸들러
    public void insertWishProduct(WishListRequestDto wishListRequestDto) {
        WishListId wishListId = getWishListId(wishListRequestDto);

        // 이미 위시리스트에 존재하는 제품을 또 추가하는 경우 하나 늘려주기
        if (wishListRepository.existsById(wishListId)) {
            increaseWishProduct(wishListRequestDto);
            return;
        }

        // 그렇지 않다면 1개 제품 추가
        WishList wishList = new WishList(wishListId, 1);
        wishListRepository.save(wishList);
    }

    // 위시리스트를 읽어오는 핸들러
    @Transactional(readOnly = true)
    public List<WishListResponseDto> readWishProducts(long userId, @Valid PageRequestDto pageRequestDto) {
        int pageNumber = pageRequestDto.pageNumber();
        int pageSize = PageRequestDto.PAGE_SIZE;
        Sort sort = SortingStateUtility.getSort(pageRequestDto.sortingState());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // 특정 userId를 갖는 위시리스트 불러오기
        Page<WishList> wishProducts = wishListRepository.findByWishListIdUserUserId(userId, pageable);

        return wishProducts.stream().map(wishList -> {
           long wishUserId = wishList.getWishListId().getUser().getUserId();
           long wishProductId = wishList.getWishListId().getProduct().getProductId();
           String name = wishList.getWishListId().getProduct().getName();
           int price = wishList.getWishListId().getProduct().getPrice();
           String imageUrl = wishList.getWishListId().getProduct().getImage();
           int quantity = wishList.getQuantity();

           return new WishListResponseDto(wishUserId, wishProductId, name, price, imageUrl, quantity);
        }).collect(Collectors.toList());
    }

    // 개수 증가하는 핸들러
    public void increaseWishProduct(WishListRequestDto wishListRequestDto) {
        WishListId wishListId = getWishListId(wishListRequestDto);

        WishList actualWishList = wishListRepository.findById(wishListId).get();
        int afterQuantity = actualWishList.getQuantity() + 1;

        actualWishList.updateQuantity(afterQuantity);
    }

    // 개수 감소하는 핸들러
    public void decreaseWishProduct(WishListRequestDto wishListRequestDto) {
        WishListId wishListId = getWishListId(wishListRequestDto);

        WishList actualWishList = wishListRepository.findById(wishListId).get();
        int afterQuantity = actualWishList.getQuantity() - 1;

        // 만약 수정하려고 하는 양이 0 이하라면, 제품을 삭제합니다.
        if (afterQuantity < 1) {
            deleteWishProduct(wishListRequestDto);
            return;
        }

        actualWishList.updateQuantity(afterQuantity);
    }

    // 위시리스트에서 제품을 삭제하는 핸들러
    public void deleteWishProduct(WishListRequestDto wishListRequestDto) {
        WishListId wishListId = getWishListId(wishListRequestDto);

        verifyWishProductExistence(wishListId);
        wishListRepository.deleteById(wishListId);
    }

    // get()을 사용하지 않는 delete 작업에서 사용할 검증
    private void verifyWishProductExistence(WishListId wishListId) {
        if (!wishListRepository.existsById(wishListId)) {
            throw new IllegalArgumentException("이미 삭제된 제품입니다.");
        }
    }

    // WishListId를 불러오기 위해 user를 찾고, product를 찾고, new WishListId를 하는 과정을 메서드로 담아서 중복을 줄였습니다.
    private WishListId getWishListId(WishListRequestDto wishListRequestDto) {
        User actualUser = permissionRepository.findById(wishListRequestDto.userId()).get();
        Product actualProduct = productRepository.findById(wishListRequestDto.productId()).get();
        return new WishListId(actualUser, actualProduct);
    }
}
