package gift.repository;

import gift.model.Member;
import gift.model.Product;
import gift.model.WishProduct;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishProductRepository extends BaseRepository<WishProduct, Long> {
    WishProduct save(WishProduct wishProduct);

    boolean existsByProductAndMember(Product product, Member member);

    Optional<WishProduct> findById(Long id);

    WishProduct findByProductAndMember(Product product, Member member);

    List<WishProduct> findAllByMemberId(Long memberId);

    void deleteById(Long id);
}
