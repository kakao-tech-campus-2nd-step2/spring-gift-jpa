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

    private final WishRepository wishRepository;

    @Autowired
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<WishResponse> getWishesByMemberId(Long memberId) {
        return wishRepository.findByMemberId(memberId).stream()
                .map(this::convertToWishResponse)
                .collect(Collectors.toList());
    }

    public Wish addWish(Member member, Product product) {
        Wish wish = Wish.builder()
                .member(member)
                .product(product)
                .build();
        return wishRepository.save(wish);
    }

    public void deleteWish(Long wishId) {
        if (!wishRepository.existsById(wishId)) {
            throw new IllegalArgumentException("Wish not found with id: " + wishId);
        }
        wishRepository.deleteById(wishId);
    }

    private WishResponse convertToWishResponse(Wish wish) {
        return WishResponse.builder()
                .id(wish.getId())
                .productName(wish.getProduct().getName())
                .productPrice(wish.getProduct().getPrice())
                .productImageurl(wish.getProduct().getImageurl())
                .build();
    }
}
