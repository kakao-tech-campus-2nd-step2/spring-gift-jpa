package gift.web.dto.response.member;

public class CreateMemberResponse {

    private Long id;

    private CreateMemberResponse() {
    }

    public CreateMemberResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
