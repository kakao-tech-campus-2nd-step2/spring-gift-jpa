package gift.wishlist.service;

import gift.common.exception.WishNotFoundException;
import gift.member.model.Member;
import gift.wishlist.dto.WishRequest;
import gift.wishlist.dto.WishResponse;
import gift.wishlist.model.Wish;
import gift.wishlist.repository.WishRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public WishResponse addWish(Member member, WishRequest request) {
        Wish wish = new Wish();
        wish.setMember(member);
        wish.setProduct(request.getProduct());

        wishRepository.save(wish);
        return new WishResponse(wish.getId(), wish.getProduct());
    }

    public List<WishResponse> getWishes(Member member) {
        List<Wish> wishes = wishRepository.findByMemberId(member.getId());
        return wishes.stream()
            .map(wish -> new WishResponse(wish.getId(), wish.getProduct()))
            .collect(Collectors.toList());
    }

    public Page<WishResponse> getWishes(Member member, Pageable pageable) {
        Page<Wish> wishesPage = wishRepository.findByMemberId(member.getId(), pageable);
        return wishesPage.map(wish -> new WishResponse(wish.getId(), wish.getProduct()));
    }

    public void deleteWishByProductId(Member member, Long productId) {
        List<Wish> wishes = wishRepository.findByMemberIdAndProductId(member.getId(), productId);
        if (wishes.isEmpty()) {
            throw new WishNotFoundException(
                "Wishlist에 Product ID: " + productId + "인 상품은 존재하지 않습니다.");
        }
        wishRepository.delete(wishes.get(0));
    }

    public void deleteWishById(Long id) {
        if (!wishRepository.existsById(id)) {
            throw new WishNotFoundException("Wishlist에 ID: " + id + "인 상품은 존재하지 않습니다.");
        }
        wishRepository.deleteById(id);
    }

}
