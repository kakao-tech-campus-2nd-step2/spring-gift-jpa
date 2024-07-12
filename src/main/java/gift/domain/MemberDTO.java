package gift.domain;

public class MemberDTO {
    private Long id;
    private String email;
    private String password;

    // 기본 생성자 추가
    public MemberDTO() {}

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(Long id) {this.id = id;}

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public Long getId(){return id;}

}