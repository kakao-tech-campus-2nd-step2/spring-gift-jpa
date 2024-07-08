package gift.domain.user.dto;



/**
 * 토큰에서 추출한 사용자 정보를 담음
 */
public class UserInfo {

    private String email;
    private Long id;

    public UserInfo(String email, Long id) {
        this.email = email;
        this.id = id;
    }

    public UserInfo() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
               "email='" + email + '\'' +
               ", id=" + id +
               '}';
    }
}
