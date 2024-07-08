package gift.mapper;

import gift.DTO.ProductDTO;
import gift.domain.Product.ProductSimple;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public List<ProductSimple> productSimpleList(List<ProductDTO> li) {
        List<ProductSimple> list = new ArrayList<>();

        for (ProductDTO p : li) {
            list.add(new ProductSimple(p.getId(), p.getName()));
        }
        return list;
    }

}
