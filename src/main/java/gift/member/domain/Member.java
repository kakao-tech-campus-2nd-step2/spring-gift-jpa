package gift.member.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table("members")
public class Member {
    @Id
    private Long id;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private Email email;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private Password password;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private NickName nickName;

    // JDBC 에서 엔티티 클래스를 인스턴스화할 때 반드시 기본 생성자와 파라미터 생성자가 필요하다
    public Member() {}

    public Member(Long id, Email email, Password password, NickName nickName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public NickName getNickName() {
        return nickName;
    }

    public boolean checkNew() {
        return id == null;
    }

    // Product 의 경우 Setter 가 없어도 정상적으로 Optional<Product> 가 반환이 되는데,
    // 이유는 모르겠지만, Member 는 Setter 가 없으면 오류가 발생한다
    // 아무리 보아도 형태가 다 같은데, 오류가 발생하는 이유가 뭘까?
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setNickName(NickName nickName) {
        this.nickName = nickName;
    }
}
