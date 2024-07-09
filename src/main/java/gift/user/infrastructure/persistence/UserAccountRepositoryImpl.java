package gift.user.infrastructure.persistence;

import gift.core.domain.user.User;
import gift.core.domain.user.UserAccount;
import gift.core.domain.user.UserAccountRepository;
import gift.core.domain.user.exception.UserAccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository {
    private final JpaUserAccountRepository jpaUserAccountRepository;

    public UserAccountRepositoryImpl(JpaUserAccountRepository jpaUserAccountRepository) {
        this.jpaUserAccountRepository = jpaUserAccountRepository;
    }

    @Override
    public void save(Long userId, UserAccount userAccount) {
        jpaUserAccountRepository.save(mapToUserAccountEntity(userAccount));
    }

    @Override
    public boolean exists(Long userId) {
        return jpaUserAccountRepository.existsById(userId);
    }

    @Override
    public boolean existsByPrincipal(String principal) {
        return jpaUserAccountRepository.existsByEmail(principal);
    }

    @Override
    public UserAccount findByUserId(Long userId) {
        return jpaUserAccountRepository.findById(userId)
                .map(this::mapToUserAccount)
                .orElseThrow();
    }

    @Override
    public UserAccount findByPrincipal(String principal) {
        return jpaUserAccountRepository.findByEmail(principal)
                .map(this::mapToUserAccount)
                .orElse(null);
    }

    @Override
    public Long findUserIdByPrincipal(String principal) {
        return jpaUserAccountRepository.findByEmail(principal)
                .map(UserAccountEntity::getUserId)
                .orElse(null);
    }

    @Override
    public void delete(Long userId) {
        jpaUserAccountRepository.deleteById(userId);
    }

    private UserAccount mapToUserAccount(UserAccountEntity entity) {
        return new UserAccount(entity.getEmail(), entity.getPassword());
    }

    private UserAccountEntity mapToUserAccountEntity(UserAccount userAccount) {
        return new UserAccountEntity(0L, userAccount.principal(), userAccount.credentials());
    }
}
