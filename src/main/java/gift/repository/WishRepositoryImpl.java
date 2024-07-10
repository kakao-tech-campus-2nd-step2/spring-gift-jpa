package gift.repository;

import gift.domain.Wish;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class WishRepositoryImpl implements WishRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Wish> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Wish.class, id));
    }

    @Override
    public List<Wish> findByMemberId(Long memberId) {
        return entityManager.createQuery("SELECT w FROM Wish w WHERE w.member.id = :memberId", Wish.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<Wish> findByProductId(Long productId) {
        return entityManager.createQuery("SELECT w FROM Wish w WHERE w.product.id = :productId", Wish.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    public Wish save(Wish wish) {
        entityManager.persist(wish);
        return wish;
    }

    @Override
    public void delete(Wish wish) {
        entityManager.remove(entityManager.contains(wish) ? wish : entityManager.merge(wish));
    }

    @Override
    public List<Wish> findByUserId(Long memberId) {
        return entityManager.createQuery("SELECT w FROM Wish w WHERE w.member.id = :memberId", Wish.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public void deleteByUserIdAndProductId(Long userId, Long productId) {
        entityManager.createQuery("DELETE FROM Wish w WHERE w.member.id = :userId AND w.product.id = :productId")
                .setParameter("userId", userId)
                .setParameter("productId", productId)
                .executeUpdate();
    }
}
