package gift.dto;

import gift.domain.Member;

public class MemberResponseDto {
    private Long id;
    private String email;

    public MemberResponseDto(String email) {
        this.email = email;
    }

    public MemberResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
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

    public static MemberResponseDto from(final Member member){
        return new MemberResponseDto(member.getId(), member.getEmail());
    }
}
