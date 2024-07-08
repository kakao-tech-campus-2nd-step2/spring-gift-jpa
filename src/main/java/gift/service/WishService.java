package gift.service;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    @Autowired
    private WishRepository wishRepository;

    public List<Wish> getWishesByMemberId(Long memberId) {
        return wishRepository.findByMemberId(memberId);
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
}