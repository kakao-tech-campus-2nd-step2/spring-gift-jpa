package gift.core.domain.user;

public interface UserRepository {

    void save(User user);

    boolean existsById(Long id);

    User findById(Long id);

    void deleteById(Long id);

}
