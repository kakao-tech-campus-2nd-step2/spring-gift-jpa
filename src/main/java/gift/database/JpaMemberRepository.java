package gift.database;

import gift.model.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemberRepository extends JpaRepository<Member, Long> {

    @Override
    <S extends Member> S save(S entity);

    @Override
    Optional<Member> findById(Long id);

    @Override
    void delete(Member member);

    Optional<Member> findByEmail(String email);

    @Override
    List<Member> findAll();
}
