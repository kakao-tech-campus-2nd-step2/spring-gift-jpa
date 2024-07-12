package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService{
    private final WishRepository wishRepository;
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getWishesByMember(Member member) {
        return member.getWishes().stream()
                .filter(w -> !w.isDeleted())
                .collect(Collectors.toList());
    }

    public void addWish(Member member, Product product) {
        Wish wish = new Wish();
        wish.setMember(member);
        wish.setProduct(product);
        wishRepository.save(wish);
    }

    public void deleteWish(Wish wish) {
        wish.setDeleted(true);
    }

}
