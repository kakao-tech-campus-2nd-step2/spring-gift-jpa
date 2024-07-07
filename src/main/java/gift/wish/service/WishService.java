package gift.wish.service;

import gift.member.domain.Member;
import gift.member.exception.MemberNotFoundException;
import gift.wish.domain.Wish;
import gift.wish.dto.WishServiceDto;
import gift.wish.exception.WishNotFoundException;
import gift.wish.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<Wish> getAllWishesByMember(Member member) {
        return wishRepository.findAll(member.getId());
    }

    public Wish getWishById(Long id) {
        return wishRepository.findById(id)
                .orElseThrow(WishNotFoundException::new);
    }

    public void createWish(WishServiceDto wishServiceDto) {
        wishRepository.save(wishServiceDto.toWish());
    }

    public void updateWish(WishServiceDto wishServiceDto) {
        validateWishExists(wishServiceDto.id());
        wishRepository.save(wishServiceDto.toWish());
    }

    public void deleteWish(Long id) {
        validateWishExists(id);
        wishRepository.deleteById(id);
    }

    private void validateWishExists(Long id) {
        wishRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

}
