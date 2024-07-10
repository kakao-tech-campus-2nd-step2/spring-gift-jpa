package gift.repository;

import gift.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Member> findById(long id) {
        return Optional.ofNullable(entityManager.find(Member.class, id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return entityManager.createQuery("SELECT m FROM Member m WHERE m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

    @Override
    public void save(Member member) {
        entityManager.persist(member);
    }

    @Override
    public void update(Member member) {
        entityManager.merge(member);
    }

    @Override
    public void delete(Member member) {
        entityManager.remove(entityManager.contains(member) ? member : entityManager.merge(member));
    }

    @Override
    public boolean existsByEmail(String email) {
        Long count = entityManager.createQuery("SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }
}
