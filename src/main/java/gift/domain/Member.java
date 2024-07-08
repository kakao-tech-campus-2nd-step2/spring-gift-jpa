package gift.domain;

import gift.dto.request.MemberRequestDto;

public class Member {

    private Long id;
    private String email;
    private String password;

    public Member() {
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Member toEntity(MemberRequestDto memberRequestDto){
        return new Member(memberRequestDto.email(), memberRequestDto.password());
    }
}
