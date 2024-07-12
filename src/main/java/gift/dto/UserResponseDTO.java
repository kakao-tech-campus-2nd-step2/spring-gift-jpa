package gift.dto;

public class UserResponseDTO {
    private Long id;
    private String email;

    public UserResponseDTO(Long id, String email) {
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