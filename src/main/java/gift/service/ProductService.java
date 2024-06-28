package gift.service;

import gift.domain.Product;
import gift.dto.CreateProductDto;
import gift.dto.UpdateProductDto;
import gift.repository.ProductRepository;
import gift.validation.ProductValidation;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidation productValidation;

    public ProductService(ProductRepository productRepository, ProductValidation productValidation) {
        this.productRepository = productRepository;
        this.productValidation = productValidation;
    }
    //private final Map<Long, Product> products = new HashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    public Product createProduct(CreateProductDto productDto) {
        Product product = new Product();

        productValidation.validateProductDto(productDto);
        //validateProductDto(productDto);
/*        if (productDto.getName() == null || productDto.getDescription() == null || productDto.getPrice() == null || productDto.getImageUrl() == null) {
            throw new IllegalArgumentException("상품의 이름, 가격, 설명을 모두 입력해야합니다.");
        }*/
        product.setId(id.getAndIncrement());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());

        productRepository.save(product);

        return product;

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long productId) {
        Product product = productRepository.findById(productId);
        productValidation.validateProductExists(productId, productRepository);
        //validateProductExists(productId);
/*        if (product == null) {
            throw new IllegalArgumentException("일치하는 상품이 없습니다.");
        }*/
        return product;
    }

    public Product updateProduct(Long productId, UpdateProductDto productDto) {
        Product product = productRepository.findById(productId);
        productValidation.validateProductExists(productId, productRepository);
        //validateProductExists(productId);
/*        if (product == null) {
            throw new IllegalArgumentException("일치하는 상품이 없습니다.");
        }*/

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());

        productRepository.update(product);
        return product;
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId);
        productValidation.validateProductExists(productId, productRepository);
        //validateProductExists(productId);
/*        if (product == null) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }*/

        productRepository.deleteById(productId);
    }

/*
    private void validateProductDto(CreateProductDto productDto) {
        if (productDto.getName() == null || productDto.getDescription() == null || productDto.getPrice() == null || productDto.getImageUrl() == null) {
            throw new IllegalArgumentException("상품의 이름, 가격, 설명을 모두 입력해야합니다.");
        }
    }

    private void validateProductExists(Long productId) {
        if (productRepository.findById(productId) == null) {
            throw new IllegalArgumentException("일치하는 상품이 없습니다.");
        }
    }
*/


}
