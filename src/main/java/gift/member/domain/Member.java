package gift.member.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table("members")
public class Member {
    @Id
    private Long id;
    private MemberType memberType = MemberType.USER;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private Email email;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private Password password;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private NickName nickName;

    // JDBC 에서 엔티티 클래스를 인스턴스화할 때 반드시 기본 생성자와 파라미터 생성자가 필요하다
    public Member() {
    }

    public Member(Long id, MemberType memberType, Email email, Password password, NickName nickName) {
        this.id = id;
        this.memberType = memberType;
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

    public MemberType getMemberType() {
        return memberType;
    }

    public boolean checkNew() {
        return id == null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void setNickName(NickName nickName) {
        this.nickName = nickName;
    }
}
