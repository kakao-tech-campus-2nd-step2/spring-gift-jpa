package gift.mapper;

import gift.domain.Wish.wishDetail;
import gift.domain.Wish.wishSimple;
import gift.entity.ProductEntity;
import gift.entity.UserEntity;
import gift.entity.WishEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class WishMapper {

    public wishDetail toDetail(WishEntity entity) {
        wishDetail detail = new wishDetail();

        detail.setId(entity.getId());
        detail.setUserId(entity.getUserDTO().getId());
        detail.setProductId(entity.getProductDTO().getId());
        detail.setName(entity.getProductDTO().getName());
        detail.setPrice(entity.getProductDTO().getPrice());
        detail.setImageUrl(entity.getProductDTO().getImageUrl());

        return detail;
    }

    public List<wishSimple> toSimpleList(List<WishEntity> li) {
        List<wishSimple> simple = new ArrayList<>();

        for (WishEntity w : li) {
            simple.add(
                new wishSimple(w.getId(), w.getUserDTO().getId(), w.getProductDTO().getId()));
        }

        return simple;
    }

    public WishEntity toEntity(UserEntity userEntity, ProductEntity productEntity) {
        return new WishEntity(productEntity, userEntity);
    }

}
