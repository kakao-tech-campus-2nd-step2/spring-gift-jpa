package gift.dto;

import gift.domain.Member;

public class MemberResponseDto {
    private Long id;
    private String email;
    private String password;

    public MemberResponseDto(Long id, String email, String password) {
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

    public static MemberResponseDto from(final Member member){
        return new MemberResponseDto(member.getId(), member.getEmail(), member.getPassword());
    }


}
