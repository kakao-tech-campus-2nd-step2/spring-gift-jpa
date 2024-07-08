package gift.service;


import gift.DTO.ProductDTO;
import gift.domain.Product;
import gift.domain.Product.ProductSimple;
import gift.errorException.BaseHandler;
import gift.mapper.ProductMapper;
import gift.repository.ProductRepository;
import gift.util.CheckName;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired

    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> getProductList() {
        return productRepository.getList();
    }

    public List<ProductSimple> getSimpleProductList() {
        return productMapper.productSimpleList(productRepository.getList());
    }

    public ProductDTO getProduct(Long id) {
        if (!productRepository.validateId(id)) {
            throw new BaseHandler(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다.");
        }
        return productRepository.getProduct(id);
    }

    public int createProduct(Product.CreateProduct create) {
        if (CheckName.checkKako(create.getName())) {
            throw new BaseHandler(HttpStatus.FORBIDDEN, "카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        }
        return productRepository.createProduct(create);
    }

    public int updateProduct(Product.UpdateProduct update, Long id) {
        if (!productRepository.validateId(id)) {
            throw new BaseHandler(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다.");
        }

        if (CheckName.checkKako(update.getName())) {
            throw new BaseHandler(HttpStatus.FORBIDDEN, "카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        }

        return productRepository.updateProduct(id, update);
    }

    public int deleteProduct(Long id) {
        if (!productRepository.validateId(id)) {
            throw new BaseHandler(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다.");
        }
        return productRepository.deleteProduct(id);
    }

}
