package gift.dto;

public class LoginResultDto {
    private String token;
    private boolean success;

    public LoginResultDto(String token, boolean success) {
        this.token = token;
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public boolean isSuccess() {
        return success;
    }
}
