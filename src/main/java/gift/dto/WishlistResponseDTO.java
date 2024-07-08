package gift.dto;

import gift.model.Product;

import java.util.List;

public record WishlistResponseDTO(Long userID,
                                  List<Product> products) {
}
