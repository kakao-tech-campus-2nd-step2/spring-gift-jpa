package gift.core.domain.user;

public interface UserAccountRepository {

    void save(Long userId, UserAccount userAccount);

    boolean exists(Long userId);

    boolean existsByPrincipal(String principal);

    UserAccount findByUserId(Long userId);

    UserAccount findByPrincipal(String principal);

    Long findUserIdByPrincipal(String principal);

    void delete(Long userId);

}
