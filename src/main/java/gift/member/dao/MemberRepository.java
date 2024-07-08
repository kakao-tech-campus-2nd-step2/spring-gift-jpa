package gift.member.dao;

import gift.member.entity.Member;
import io.hypersistence.utils.spring.repository.HibernateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends
        HibernateRepository<Member>,
        JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

}