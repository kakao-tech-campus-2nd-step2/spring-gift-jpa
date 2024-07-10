package gift.product.business.service;

import gift.product.business.dto.ProductPagingDto;
import gift.product.persistence.entity.Product;
import gift.product.persistence.repository.ProductRepository;
import gift.product.business.dto.ProductDto;
import gift.product.business.dto.ProductRegisterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public ProductDto getProduct(Long id) {
        Product product = productRepository.getProductById(id);
        return ProductDto.from(product);
    }

    public Long createProduct(ProductRegisterDto productRegisterDto) {
        Product product = productRegisterDto.toProduct();
        return productRepository.saveProduct(product);
    }

    public Long updateProduct(ProductRegisterDto productRegisterDto, Long id) {
        var product = productRepository.getProductById(id);
        product.update(productRegisterDto.name(), productRegisterDto.description(),
                productRegisterDto.price(), productRegisterDto.url());
        return productRepository.saveProduct(product);
    }

    public Long deleteProduct(Long id) {
        return productRepository.deleteProductById(id);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        return ProductDto.of(products);
    }

    public void deleteProducts(List<Long> productIds) {
        productRepository.deleteProductByIdList(productIds);
    }

    public ProductPagingDto getProductsByPage(int page) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        Page<Product> products = productRepository.getProductsByPage(pageRequest);
        return ProductPagingDto.from(products);
    }
}
