package gift.repository;

import gift.model.Member;
import java.util.Optional;

public interface MemberRepository {
  Member save(Member member);
  Optional<Member> findByEmail(String email);
  Optional<Member> findById(Long id);
}
