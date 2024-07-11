package gift.domain.user.dto;


/**
 * 토큰에서 추출한 사용자 정보를 담음
 */
public class UserInfo {

    private Long id;
    private String email;

    public UserInfo(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserInfo() {
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
               "email='" + email + '\'' +
               ", id=" + id +
               '}';
    }
}
