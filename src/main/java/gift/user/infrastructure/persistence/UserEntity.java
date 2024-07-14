package gift.user.infrastructure.persistence;

import gift.core.domain.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "`user`")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public UserEntity() {
    }

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static UserEntity from(User user) {
        return new UserEntity(user.id(), user.name());
    }
}
