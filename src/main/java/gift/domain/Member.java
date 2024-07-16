package gift.domain;

import gift.domain.vo.Email;
import gift.domain.vo.Password;
import gift.web.validation.exception.client.IncorrectPasswordException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Member extends BaseTimeEntity {

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<WishProduct> wishProducts;

    protected Member() {
    }

    public static class Builder extends BaseTimeEntity.Builder<Member.Builder> {

        private Email email;
        private Password password;
        private String name;

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Member build() {
            return new Member(this);
        }
    }

    private Member(Builder builder) {
        super(builder);
        email = builder.email;
        password = builder.password;
        name = builder.name;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void matchPassword(String password) {
        if (!this.password.matches(password)) {
            throw new IncorrectPasswordException();
        }
    }
}
