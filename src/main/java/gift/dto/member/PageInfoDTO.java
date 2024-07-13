package gift.dto.member;

public record PageInfoDTO(
        int page,
        int size,
        String sort,
        boolean asc
) {
}
