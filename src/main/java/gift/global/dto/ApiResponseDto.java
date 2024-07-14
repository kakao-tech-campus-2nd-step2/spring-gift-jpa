package gift.global.dto;

// 반환이 없는 api의 반환은 이 dto를 반환합니다.
public record ApiResponseDto(String response) {

    // 성공한 경우는 SUCCESS를 반환, 그렇지 않은 경우는 예외 핸들러에서 예외 메시지 반환
    public final static String SUCCESS = "success";
}
