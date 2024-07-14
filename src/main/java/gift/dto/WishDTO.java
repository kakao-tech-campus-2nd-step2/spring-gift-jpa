package gift.dto;

import gift.entity.Product;

public record WishDTO(Long id, Product product, Integer quantity) {

}