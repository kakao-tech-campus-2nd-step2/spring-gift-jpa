package gift.dto;

import gift.domain.Member;

public class MemberRequestDto {
    private Long id;
    private String email;
    private String password;

    public MemberRequestDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Member toEntity(){
        return new Member(this.getId(), this.getEmail(), this.getPassword());
    }
    public Long getId() { return id; }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
