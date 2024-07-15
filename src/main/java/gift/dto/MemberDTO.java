package gift.dto;

import gift.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MemberDTO {


    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private String name;

    private String role;

    public MemberDTO() {
        this.name = "default_user";
        this.role = "USER";
    }

    public MemberDTO(String email, String password) {
        this(email, password, "default_user", "USER");
    }

    public MemberDTO(String email, String password, String name) {
        this(email, password, name, "USER");
    }

    public MemberDTO(String email, String password, String name, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public String getEmail() { return email; }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getRole() {
        return role;
    }

    public Member convertToMember() {
        return new Member(this.email, this.password, this.name, this.role);
    }

    public static MemberDTO convertToMemberDTO(Member member) {
        return new MemberDTO(member.getEmail(), member.getPassword(), member.getName(),
                member.getRole());
    }

}