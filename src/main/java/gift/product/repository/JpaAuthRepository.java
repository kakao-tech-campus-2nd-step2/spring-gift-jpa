package gift.product.repository;

import gift.product.model.Member;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

public class JpaAuthRepository implements AuthRepository {

    private final EntityManager em;

    public JpaAuthRepository(EntityManager em) {
        this.em = em;
    }

    public boolean existsByEmail(String email) {
        String jpql = "SELECT COUNT(m) FROM Member m WHERE m.email = :email";

        Long count = em.createQuery(jpql, Long.class)
            .setParameter("email", email)
            .getSingleResult();

        return count > 0;
    }

    public boolean existsById(Long id) {
        return em.find(Member.class, id) != null;
    }

    @Transactional
    public void save(Member member) {
        em.persist(member);
    }

    public Member findByEmail(String email) {
        String jpql = "SELECT m FROM Member m WHERE m.email = :email";

        return em.createQuery(jpql, Member.class)
            .setParameter("email", email)
            .getSingleResult();
    }
}
