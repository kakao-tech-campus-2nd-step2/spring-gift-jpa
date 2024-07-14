package gift.domain;

/**
 * Data Transfer Object for Member
 */
public class MemberDTO {

    private Long id;
    private String email;
    private String password;


    /**
     * 매개변수가 있는 생성자
     *
     * @param id 멤버 고유의 ID
     * @param email 멤버의 이메일 주소
     * @param password 멤버의 비밀번호
     */
    public MemberDTO(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}