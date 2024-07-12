package gift.wishes;

import gift.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByMemberId(Long memberId);

    void deleteByIdAndMember(Long id, Member member);

    Optional<Wish> findByIdAndMember(Long id, Member member);
}
