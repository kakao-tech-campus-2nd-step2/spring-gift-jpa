package gift.dto;

public class MemberRegisterResponseDto {
    private final Long id;
    private final String email;
    private final String name;

    public MemberRegisterResponseDto(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
