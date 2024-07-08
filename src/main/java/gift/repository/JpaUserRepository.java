package gift.repository;

import gift.model.User;
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
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> user = em.createQuery("select u from User u where u.email = :email")
                .setParameter("email", email)
                .getResultList();
        return user.stream().findAny();
    }
}
