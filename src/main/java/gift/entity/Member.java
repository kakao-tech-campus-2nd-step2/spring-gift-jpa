package gift.entity;

import gift.dto.MemberDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="member")
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String role;
    
    protected Member() {

    }

    public Member(String password, String email, String role) {
        this.password = password;
        this.email = email;
        this.role = role;
    }
    
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public MemberDto toDto(){
        return new MemberDto(this.id, this.password, this.email, this.role);
    }
}