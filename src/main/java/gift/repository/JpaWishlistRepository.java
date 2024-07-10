package gift.repository;

import gift.model.WishList;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public class JpaWishlistRepository implements WishlistRepository {

    private final EntityManager em;

    public JpaWishlistRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean save(WishList wishList) {
        try {
            em.persist(wishList);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(String email) {
        int result = em.createQuery("delete from WishList w where w.email=:email")
                .setParameter("email", email)
                .executeUpdate();
        return result > 0;
    }

    @Override
    public WishList findByEmail(String email) {
        Optional<WishList> result = em.createQuery("select w from WishList w where w.email=:email")
                .setParameter("email", email)
                .getResultList().stream().findAny();
        if (!result.isPresent()) {
            return new WishList(email);
        }
        return result.get();
    }
}
