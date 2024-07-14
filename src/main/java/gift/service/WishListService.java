package gift.service;

import gift.controller.WishListController;
import gift.domain.WishList;
import gift.domain.WishListRequest;
import gift.domain.WishListResponse;
import gift.repository.WishListRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishListService {
    WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void save(WishListRequest wishListRequest) {
        WishList wishList = WishListController.MapWishListRequestToWishList(wishListRequest);
        wishListRepository.save(wishList);
    }

    public List<WishListResponse> findById(String jwtId, Pageable pageable) {
        List<WishList> wishLists =  wishListRepository.findByMemberId(jwtId,pageable);
        return wishLists.stream()
                .map(WishListController::MapWishListToWishListResponse)
                .collect(Collectors.toList());
    }

    public void delete(Long Id) {
        wishListRepository.deleteById(Id);
    }
}

