package gift.converter;

import gift.dto.NameDTO;
import gift.model.Name;
import gift.model.Product;
import gift.dto.ProductDTO;

public class ProductConverter {

    public static ProductDTO convertToDTO(Product product) {
        NameDTO nameDTO = NameConverter.convertToDTO(product.getName());
        return new ProductDTO(product.getId(), nameDTO, product.getPrice(), product.getImageUrl());
    }

    public static Product convertToEntity(ProductDTO productDTO) {
        Name name = NameConverter.convertToEntity(productDTO.getName());
        return new Product(productDTO.getId(), name, productDTO.getPrice(), productDTO.getImageUrl());
    }
}