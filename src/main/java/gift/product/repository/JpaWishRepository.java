package gift.product.repository;

import gift.product.dto.LoginMember;
import gift.product.model.Wish;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
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

    public List<Wish> findAllByMemberId(Long memberId) {
        String jpql = "SELECT w FROM Wish w WHERE w.memberId = :memberId";

        return em.createQuery(jpql, Wish.class)
            .setParameter("memberId", memberId)
            .getResultList();
    }

    public Optional<Wish> findByIdAndMemberId(Long id, Long memberId) {
        String jpql = "SELECT w FROM Wish w WHERE w.id = :id AND w.memberId = :memberId";
        return em.createQuery(jpql, Wish.class)
            .setParameter("id", id)
            .setParameter("memberId",memberId)
            .getResultList().stream().findAny();
    }

    @Transactional
    public void deleteById(Long id) {
        Wish wish = em.find(Wish.class, id);
        em.remove(wish);
    }
}
