package gift.mapper;

import gift.domain.Product;
import gift.domain.Product.CreateProduct;
import gift.domain.Product.UpdateProduct;
import gift.entity.ProductEntity;
import gift.domain.Product.ProductSimple;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public List<ProductSimple> toSimpleList(List<ProductEntity> li) {
        List<ProductSimple> list = new ArrayList<>();

        for (ProductEntity p : li) {
            list.add(new ProductSimple(p.getId(), p.getName()));
        }
        return list;
    }

    public ProductEntity toEntity(CreateProduct create){
        return new ProductEntity(create.getName(),create.getPrice(),create.getImageUrl());
    }

    public ProductEntity toUpdate(UpdateProduct update, ProductEntity entity){
        entity.setPrice(update.getPrice());
        entity.setName(update.getName());
        entity.setImageUrl(update.getImageUrl());
        return entity;
    }

}
