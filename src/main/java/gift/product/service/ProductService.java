package gift.product.service;

import gift.product.Product;
import gift.product.dto.ProductReqDto;
import gift.product.dto.ProductResDto;
import gift.product.exception.ProductNotFoundException;
import gift.product.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResDto> getProducts() {
        return productRepository.findProducts().stream()
                .map(ProductResDto::new)
                .toList();
    }

    public ProductResDto getProduct(Long productId) {
        Product findProduct = productRepository.findProductByIdOrThrow(productId);
        return new ProductResDto(findProduct);
    }

    public ProductResDto addProduct(ProductReqDto productReqDto) {
        Long productId = productRepository.addProduct(productReqDto);
        Product newProduct = productRepository.findProductByIdOrThrow(productId);
        return new ProductResDto(newProduct);
    }

    public void updateProduct(Long productId, ProductReqDto productReqDto) {
        validateProductExists(productId);
        productRepository.updateProductById(productId, productReqDto);
    }

    public void deleteProduct(Long productId) {
        validateProductExists(productId);
        productRepository.deleteProductById(productId);
    }

    private void validateProductExists(Long productId) {
        boolean isExist = productRepository.isProductExistById(productId);

        if (!isExist) {
            throw ProductNotFoundException.EXCEPTION;
        }
    }
}
