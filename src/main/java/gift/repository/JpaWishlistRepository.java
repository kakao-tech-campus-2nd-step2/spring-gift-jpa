package gift.repository;

import gift.model.WishList;
import gift.model.WishListDTO;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class JpaWishlistRepository implements WishlistRepository {

    private final EntityManager em;

    public JpaWishlistRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean addWishlist(String email, WishListDTO wishlist) {
        try {
            em.persist(new WishList(email, wishlist.getProductId(), wishlist.getCount()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeWishlist(String email, Long productId) {
        int result = em.createQuery("delete from WishList w where w.email=:email and w.productId=:productId")
                .setParameter("email", email)
                .setParameter("productId", productId)
                .executeUpdate();
        return result > 0;
    }

    @Override
    public boolean updateWishlist(String email, WishListDTO wishlist) {
        Optional<WishList> result = em.createQuery("select w from WishList w where w.email=:email and w.productId=:productId")
                .setParameter("email", email)
                .setParameter("productId", wishlist.getProductId())
                .getResultList().stream().findAny();
        if (!result.isPresent()) return false;
        WishList updatedWishlist = result.get();
        updatedWishlist.setCount(wishlist.getCount());
        em.merge(updatedWishlist);
        return true;
    }

    @Override
    public List<WishList> getMyWishlists(String email) {
        return em.createQuery("select w from WishList w where w.email=:email")
                .setParameter("email", email)
                .getResultList();
    }
}
