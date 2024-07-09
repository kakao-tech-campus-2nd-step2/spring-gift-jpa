package gift.service;


import gift.domain.Product;
import gift.domain.Product.ProductSimple;
import gift.domain.Product.getList;
import gift.entity.ProductEntity;
import gift.errorException.BaseHandler;
import gift.mapper.ProductMapper;
import gift.repository.ProductRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    public Page<ProductEntity> getProductList(getList param) {

        return productRepository.findAll(param.toPageable());
    }

    public Page<ProductSimple> getSimpleProductList(getList param) {
        return productMapper.toSimpleList(productRepository.findAll(param.toPageable()));
    }

    public ProductEntity getProduct(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다."));
    }

    public Long createProduct(Product.CreateProduct create) {
        ProductEntity productEntity = productMapper.toEntity(create);

        productRepository.save(productEntity);
        return productEntity.getId();
    }

    public Long updateProduct(Product.UpdateProduct update, Long id) {
        ProductEntity productEntity = productRepository.findById(id)
            .orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다."));

        productRepository.save(productMapper.toUpdate(update, productEntity));
        return productEntity.getId();
    }

    public Long deleteProduct(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
            .orElseThrow(() -> new BaseHandler(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다."));

        productRepository.delete(productEntity);
        return productEntity.getId();
    }

}
