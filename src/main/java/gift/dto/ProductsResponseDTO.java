package gift.dto;

import gift.model.Product;
import java.util.List;

public record ProductsResponseDTO(List<Product> productDTOList) {
}
