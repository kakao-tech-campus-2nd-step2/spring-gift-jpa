package gift.wishlist.service;

import gift.wishlist.dto.WishListRequestDto;
import gift.wishlist.dto.WishListResponseDto;
import gift.wishlist.repository.WishListRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    // 추가
    public void insertWishProduct(WishListRequestDto wishListRequestDto) {
        // insertWishProduct의 반환값이 이미 key를 가진 로우가 있어서 삽입에 실패했는지를 나타냅니다.
        // 메서드 이름만 보면 void처럼 보여서 key를 가진 로우가 존재하는지에 대한 검증을 service에서 했어야 할지도 고민을 했었습니다.

        // 만약 이미 있는 요소에 insert 요청을 넣었다면
        // 위시 리스트의 제품 개수 하나 늘리기
    }

    // 읽기
    public List<WishListResponseDto> readWishProducts(long userId) {
        return new ArrayList<>();
    }

    // 개수 증가
    public void increaseWishProduct(WishListRequestDto wishListRequestDto) {
    }

    // 개수 감소
    public void decreaseWishProduct(WishListRequestDto wishListRequestDto) {
        // 만약 수정하려고 하는 양이 0 이하라면, 제품을 삭제합니다.
    }

    // 삭제
    public void deleteWishProduct(WishListRequestDto wishListRequestDto) {
    }
}
