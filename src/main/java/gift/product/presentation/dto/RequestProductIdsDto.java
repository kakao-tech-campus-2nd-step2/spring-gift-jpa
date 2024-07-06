package gift.product.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RequestProductIdsDto(
    @NotEmpty
    @Size(min = 1, max = 10)
    List<Long> productIds
) {

}
