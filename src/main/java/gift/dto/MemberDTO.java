package gift.dto;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.exception.BadRequestExceptions.BadRequestException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

public class MemberDTO {


    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private String name;

    private String role;

    private List<Wish> wishList;

    public MemberDTO() {
        this.name = "default_user";
        this.role = "USER";
    }

    public MemberDTO(String email, String password) {
        this(email, password, "default_user", "USER", new ArrayList<>());
    }

    public MemberDTO(String email, String password, String name) {
        this(email, password, name, "USER", new ArrayList<>());
    }

    public MemberDTO(String email, String password, String name, String role, List<Wish> wishList) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.wishList = wishList;
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
    public List<Wish> getWishList() { return wishList; }

    public static Member convertToMember(MemberDTO memberDTO) {
        return new Member(memberDTO.getEmail(), memberDTO.getPassword(), memberDTO.getName(), memberDTO.getRole(), memberDTO.getWishList());
    }

    public static MemberDTO convertToMemberDTO(Member member) {
        return new MemberDTO(member.getEmail(), member.getPassword(), member.getName(),
                member.getRole(), member.getWishList());
    }

}