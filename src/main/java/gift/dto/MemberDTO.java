package gift.dto;

import gift.entity.Wish;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Wish> getWishList() {
        return wishList;
    }

    public void setWishList(List<Wish> wishList) {
        this.wishList = wishList;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}