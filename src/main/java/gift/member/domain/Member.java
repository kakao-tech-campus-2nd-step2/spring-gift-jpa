package gift.member.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MemberType memberType = MemberType.USER;
    @Embedded
    private Email email;
    @Embedded
    private Password password;
    @Embedded
    private Nickname nickName;

    // JDBC 에서 엔티티 클래스를 인스턴스화할 때 반드시 기본 생성자와 파라미터 생성자가 필요하다
    public Member() {
    }

    public Member(Long id, MemberType memberType, Email email, Password password, Nickname nickName) {
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

    public Nickname getNickName() {
        return nickName;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public boolean checkNew() {
        return id == null;
    }
}
