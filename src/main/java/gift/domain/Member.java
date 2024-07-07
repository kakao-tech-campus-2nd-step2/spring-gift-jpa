package gift.domain;

import gift.domain.vo.Email;
import gift.domain.vo.Password;

public class Member extends BaseEntity{

    private final Email email;
    private final Password password;
    private final String name;

    public static class Builder extends BaseEntity.Builder<Member.Builder> {

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
}
