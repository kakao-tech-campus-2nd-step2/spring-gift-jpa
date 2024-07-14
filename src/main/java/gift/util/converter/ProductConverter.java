package gift.util.converter;

import gift.dto.ProductDTO;
import gift.entity.Product;
import gift.exception.BadRequestExceptions.BadRequestException;
import org.springframework.data.domain.Page;


public class ProductConverter {
    public static Page<ProductDTO> convertToProductDTO(Page<Product> productPage) throws BadRequestException {
        return productPage.map(product -> new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getImageUrl()));
    }

    public static Product convertToProduct(ProductDTO productDTO){
        return new Product(productDTO.name(), productDTO.price(), productDTO.imageUrl());
    }

}
