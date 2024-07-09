package gift.feat.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gift.feat.user.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}