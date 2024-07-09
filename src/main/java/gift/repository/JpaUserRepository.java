package gift.repository;

import gift.model.User;
import gift.model.UserDTO;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(UserDTO userDTO) {
        User user = new User(userDTO.getEmail(), userDTO.getPassword());
        em.persist(user);

        Optional<User> result = findByEmail(userDTO.getEmail());

        return result.orElse(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> user = em.createQuery("select u from User u where u.email = :email")
                .setParameter("email", email)
                .getResultList();
        return user.stream().findAny();
    }
}
