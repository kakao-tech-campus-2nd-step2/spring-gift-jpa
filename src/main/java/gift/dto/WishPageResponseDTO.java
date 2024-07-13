package gift.dto;

import gift.model.Product;

import java.util.List;

public record WishPageResponseDTO(List<Product> products,
                                  Integer currentPage,
                                  Integer totalPages) {
}
