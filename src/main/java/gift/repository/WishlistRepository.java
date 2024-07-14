package gift.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import gift.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>{
	
	Page<Wishlist> findByUserId(Long userId, Pageable pageable);
}
