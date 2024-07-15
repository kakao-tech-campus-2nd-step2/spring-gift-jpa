package gift.service;

import gift.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    List<ProductDTO> readAll();

    List<ProductDTO> readProduct(int pageNumber, int pageSize);

    void create(ProductDTO prod);

    void updateName(long id, String name);

    void updatePrice(long id, int price);

    void updateImageUrl(long id, String url);

    void delete(long id);

}
