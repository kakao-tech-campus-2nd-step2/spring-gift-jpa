package gift.service;

import gift.dao.WishDAO;
import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.dto.WishResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {

    @Autowired
    private WishDAO wishDAO;

    public List<WishResponse> getWishes(Long memberId) {
        return wishDAO.findByMemberId(memberId).stream()
                .map(wish -> new WishResponse(wish.getId(), wish.getProductName(), wish.getMemberId()))
                .collect(Collectors.toList());
    }

    public WishResponse addWish(WishRequest wishRequest, Long memberId) {
        Wish wish = new Wish(wishRequest.getProductName(), memberId);
        wishDAO.save(wish);
        return new WishResponse(wish.getId(), wish.getProductName(), wish.getMemberId());
    }

    public void removeWish(Long wishId) {
        wishDAO.deleteById(wishId);
    }
}
