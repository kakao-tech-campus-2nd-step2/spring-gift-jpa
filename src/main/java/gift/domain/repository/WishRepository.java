package gift.domain.repository;

import gift.domain.model.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query("SELECT w FROM Wish w JOIN FETCH w.product WHERE w.user.email = :email")
    List<Wish> findByUserEmail(String email);

    Optional<Wish> findByUserEmailAndProductId(String email, Long productId);

    boolean existsByUserEmailAndProductId(String email, Long productId);

    void deleteByUserEmailAndProductId(String email, Long productId);
}