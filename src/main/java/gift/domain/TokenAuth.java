package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "token_auth")
public class TokenAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public TokenAuth(String token, Member member) {
        this.token = token;
        this.member = member;
    }

    public TokenAuth() { }

    public String getToken() {
        return token;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
