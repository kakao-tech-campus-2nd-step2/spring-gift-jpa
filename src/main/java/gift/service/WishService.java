package gift.service;

import gift.dto.WishResponse;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {

    @Autowired
    private WishRepository wishRepository;

    public List<WishResponse> getWishesByMemberId(Long memberId) {
        return wishRepository.findByMemberId(memberId).stream()
                .map(this::convertToWishResponse)
                .collect(Collectors.toList());
    }

    public Wish addWish(Member member, Product product) {
        Wish wish = new Wish();
        wish.setMember(member);
        wish.setProduct(product);
        return wishRepository.save(wish);
    }

    public void deleteWish(Long wishId) {
        wishRepository.deleteById(wishId);
    }

    private WishResponse convertToWishResponse(Wish wish) {
        WishResponse wishResponse = new WishResponse();
        wishResponse.setId(wish.getId());
        wishResponse.setProductName(wish.getProduct().getName());
        wishResponse.setProductPrice(wish.getProduct().getPrice());
        wishResponse.setProductImageurl(wish.getProduct().getImageurl());
        return wishResponse;
    }
}