package gift.dto;

import gift.entity.Wish;
import org.springframework.data.domain.Page;

public record WishListDTO(Page<Wish> wishPage) { }
