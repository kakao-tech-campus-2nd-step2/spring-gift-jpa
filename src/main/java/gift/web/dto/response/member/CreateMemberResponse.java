package gift.web.dto.response.member;

public class CreateMemberResponse {

    private final Long id;

    public CreateMemberResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
