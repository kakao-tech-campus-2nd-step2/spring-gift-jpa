package gift.controller.wish;

import gift.domain.Wish;

public record WishDto(Long id, String email, Long productId, Long count) {
}