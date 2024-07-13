package gift.util;

import gift.model.Product;
import gift.model.ProductDTO;

public class ProductUtility {
    public static Product productDTOToDAO(Product productDAO, ProductDTO productDTO) {
        productDAO.setName(productDTO.getName());
        productDAO.setPrice(productDTO.getPrice());
        productDAO.setImageUrl(productDTO.getImageUrl());
        return productDAO;
    }
}
