package gift.product.repository;

import gift.product.dto.LoginMember;
import gift.product.model.Wish;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class WishRepository {

    private final EntityManager em;

    public WishRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Wish save(Wish wish) {
        em.persist(wish);
        return wish;
    }

    public List<Wish> findAll(LoginMember loginMember) {
        String jpql = "SELECT w FROM Wish w WHERE w.memberId = :memberId";

        return em.createQuery(jpql, Wish.class)
            .setParameter("memberId", loginMember.id())
            .getResultList();
    }

    public Wish findById(Long id, LoginMember loginMember) throws Exception {
        String jpql = "SELECT w FROM Wish w WHERE w.id = :id AND w.memberId = :memberId";
        return em.createQuery(jpql, Wish.class)
            .setParameter("id", id)
            .setParameter("memberId", loginMember.id())
            .getSingleResult();
    }

    @Transactional
    public void delete(Long id) {
        Wish wish = em.find(Wish.class, id);
        em.remove(wish);
    }
}
