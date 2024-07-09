package gift.wish.repository;

import gift.global.CRUDRepository;
import gift.member.domain.Email;
import gift.member.domain.Member;
import gift.member.domain.Password;
import gift.product.domain.Product;
import gift.wish.domain.Wish;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends CRUDRepository<Wish, Long> {
    List<Wish> findAllByMemberId(Long memberId);
}
