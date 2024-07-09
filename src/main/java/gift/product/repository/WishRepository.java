package gift.product.repository;

import gift.product.dto.LoginMember;
import gift.product.model.Wish;
import java.util.List;
import java.util.Optional;

public interface WishRepository {

    public Wish save(Wish wish);

    public List<Wish> findAllByMemberId(Long memberId);

    public Optional<Wish> findByIdAndMemberId(Long id, Long memberId);

    public void deleteById(Long id);
}
