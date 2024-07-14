package gift.web.dto;

import gift.domain.wish.Wish;

public record WishDto(
    Long productId,
    Long count
    ) { }
