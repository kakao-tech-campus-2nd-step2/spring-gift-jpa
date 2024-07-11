package gift.global.dto;

// 토큰을 담는 dto. user/{user_id}/main으로 이동하기 위해 userId도 같이 담아줍니다.
public record TokenDto(String token, long userId) {

}
