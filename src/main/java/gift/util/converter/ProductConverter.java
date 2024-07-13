package gift.util.converter;

import gift.dto.ProductDTO;
import gift.entity.Product;
import gift.exception.BadRequestExceptions.BadRequestException;
import java.util.List;
import java.util.stream.Collectors;


public class ProductConverter {

    public static List<ProductDTO> convertToProductDTO(List<Product> productList)
            throws BadRequestException {
        return productList.stream().map(product -> {
            return new ProductDTO(product.getId(), product.getName(), product.getPrice(),
                    product.getImageUrl());
        }).collect(Collectors.toList());
    }

    public static Product convertToProduct(ProductDTO productDTO){
        return new Product(productDTO.name(), productDTO.price(), productDTO.imageUrl());
    }
}
