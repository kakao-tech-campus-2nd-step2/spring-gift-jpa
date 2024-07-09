package gift.product.repository;

import gift.product.model.Member;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AuthRepository {

    private final EntityManager em;

    public AuthRepository(EntityManager em) {
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
