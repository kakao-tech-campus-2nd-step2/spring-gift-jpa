package gift.util.pagenation;

public record PageInfoDTO(
        int page,
        int size,
        String sort,
        boolean asc
) {
}
