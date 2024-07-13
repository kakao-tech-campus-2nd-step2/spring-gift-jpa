package gift.user.infrastructure.persistence;

import gift.core.domain.user.User;
import gift.core.domain.user.UserAccount;
import gift.core.domain.user.UserAccountRepository;
import gift.core.domain.user.UserRepository;
import gift.core.domain.user.exception.UserAccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserRepositoryImpl(
            JpaUserRepository jpaUserRepository,
            UserAccountRepository userAccountRepository
    ) {
        this.jpaUserRepository = jpaUserRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public void save(User user) {
        UserEntity savedUser = jpaUserRepository.save(mapToUserEntity(user));
        userAccountRepository.save(savedUser.getId(), user.account());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaUserRepository.existsById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(this::mapToUser);
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }

    private User mapToUser(UserEntity entity) {
        UserAccount userAccount = userAccountRepository
                .findByUserId(entity.getId())
                .orElseThrow(UserAccountNotFoundException::new);
        return new User(
                entity.getId(),
                entity.getName(),
                userAccount
        );
    }

    private UserEntity mapToUserEntity(User user) {
        return new UserEntity(user.id(), user.name());
    }
}
