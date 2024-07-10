package gift.repository;

import gift.domain.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish,Long> {
    void deleteByProduct_IdAndUserInfo_Id(Long productId,Long userId);
    List<Wish> findByUserInfo_Id(Long userId);
    boolean existsByUserInfo_IdAndProduct_Id(Long userId , Long productId);
    Wish findByUserInfo_IdAndProduct_Id(Long userId,Long productId);
}
