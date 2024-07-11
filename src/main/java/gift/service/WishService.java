package gift.service;

import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.entity.WishEntity;
import gift.repository.WishRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;

    }
    public List<Wish> getWishesByMember(Member member) {
        return wishRepository.findAllByMemberId(member.getId()).stream().map(WishEntity::toWish).collect(
            Collectors.toList());
    }

    public Wish addWish(WishRequest wishRequest) {
        return wishRepository.save(wishRequest.toWishEntity()).toWish();
    }

    public void deleteWish(Long id, Member member) {
        WishEntity wishEntity = wishRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found entity"));

        if(member.getId() == wishEntity.getMemberId()) {
            wishRepository.delete(wishEntity);
        }
    }
}
