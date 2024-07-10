package gift.repository;

import gift.model.Member;
import gift.model.Product;
import gift.model.WishProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishProductRepository extends BaseRepository<WishProduct, Long> {
    boolean existsByProductAndMember(Product product, Member member);

    WishProduct findByProductAndMember(Product product, Member member);

    List<WishProduct> findAllByMemberId(Long memberId);
}
