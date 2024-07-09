package gift.Repository;

import gift.Model.Wishlist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

}
