package gift.dto;

import gift.model.Product;

import java.util.List;

public record WishResponseDTO(Long userID,
                                  List<Product> products) {
}
