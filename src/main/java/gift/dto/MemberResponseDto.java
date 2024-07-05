package gift.dto;

public class MemberResponseDto {
    private Long id;
    private String email;
    private String token;

    public MemberResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
