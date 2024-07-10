package gift.service;

import gift.domain.WishList;
import gift.domain.WishListRequest;
import gift.domain.WishListResponse;
import gift.repository.WishListRepository;
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
        WishList wishList = WishList.MapWishListRequestToWishList(wishListRequest);
        wishListRepository.save(wishList);
    }

    public List<WishListResponse> findById(String jwtId) {
        List<WishList> wishLists =  wishListRepository.findByMemberId(jwtId);
        return wishLists.stream()
                .map(WishList::MapWishListToWishListResponse)
                .collect(Collectors.toList());
    }

    public void delete(Long Id) {
        wishListRepository.deleteById(Id);
    }
}

