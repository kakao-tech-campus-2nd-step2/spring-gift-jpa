package gift.dto;

public class MemberResponseDTO {
    private Long id;
    private String email;

    public MemberResponseDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}