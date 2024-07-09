package gift.product.repository;

import gift.product.dto.LoginMember;
import gift.product.model.Wish;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class JpaWishRepository implements WishRepository {

    private final EntityManager em;

    public JpaWishRepository(EntityManager em) {
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
