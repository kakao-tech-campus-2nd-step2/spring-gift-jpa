package gift.main.service;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import gift.main.dto.ProductRequest;
import gift.main.dto.ProductResponce;
import gift.main.dto.UserVo;
import gift.main.entity.Product;
import gift.main.entity.User;
import gift.main.repository.ProductRepository;
import gift.main.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public Page<ProductResponce> getProductPage(Pageable pageable) {
        Page<ProductResponce> productPage = productRepository.findAll(pageable)
                .map(ProductResponce::new);
        return productPage;
    }

    @Transactional
    public void addProduct(ProductRequest productRequest, UserVo user) {
        User seller = userRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Product product = new Product(productRequest, seller);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProduct(long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        product.updateValue(productRequest);
        productRepository.save(product);
    }


    public ProductResponce getProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return new ProductResponce(product);
        //변경은 어디서?
    }


}

