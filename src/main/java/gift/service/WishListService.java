package gift.service;

import gift.domain.WishListRequest;
import gift.domain.WishListResponse;
import gift.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
    WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void create(WishListRequest wishListRequest) {
        wishListRepository.create(wishListRequest);
    }

    public List<WishListResponse> findById(String jwtId) {
        return wishListRepository.findById(jwtId);
    }

    public boolean delete(String jwtId, Long menuId) {
        if (wishListRepository.delete(jwtId, menuId)) return true;
        else return false;
    }
}