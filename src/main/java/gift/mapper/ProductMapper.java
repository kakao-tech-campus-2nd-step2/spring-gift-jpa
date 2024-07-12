package gift.mapper;

import gift.domain.Product.CreateProduct;
import gift.domain.Product.ProductSimple;
import gift.domain.Product.UpdateProduct;
import gift.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Page<ProductSimple> toSimpleList(Page<ProductEntity> all) {
        List<ProductSimple> simpleList = all.stream()
            .map(entity -> new ProductSimple(
                entity.getId(),
                entity.getName()
            ))
            .collect(Collectors.toList());

        return new PageImpl<>(simpleList, all.getPageable(), all.getTotalElements());
    }

    public ProductEntity toEntity(CreateProduct create) {
        return new ProductEntity(create.getName(), create.getPrice(), create.getImageUrl());
    }

    public ProductEntity toUpdate(UpdateProduct update, ProductEntity entity) {
        entity.setPrice(update.getPrice());
        entity.setName(update.getName());
        entity.setImageUrl(update.getImageUrl());
        return entity;
    }

}
