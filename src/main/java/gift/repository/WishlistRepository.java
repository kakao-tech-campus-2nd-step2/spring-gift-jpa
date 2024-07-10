package gift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gift.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>{
	
	List<Wishlist> findByUserId(Long userId);
}
