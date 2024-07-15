package gift.domain.user.dao;

import gift.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    @Query("select distinct u from User u left join fetch u.wishlist where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
