package gift.model.dto;

public class MemberResponseDto {

    private String accessToken;

    public MemberResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}