package gift.product.service;

import gift.product.Product;
import gift.product.dto.ProductReqDto;
import gift.product.dto.ProductResDto;
import gift.product.exception.ProductCreateException;
import gift.product.exception.ProductDeleteException;
import gift.product.exception.ProductNotFoundException;
import gift.product.exception.ProductUpdateException;
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
        Long productId;
        try {
            productId = productRepository.addProduct(productReqDto);
        } catch (Exception e) {
            throw ProductCreateException.EXCEPTION;
        }

        Product newProduct = productRepository.findProductByIdOrThrow(productId);
        return new ProductResDto(newProduct);
    }

    public void updateProduct(Long productId, ProductReqDto productReqDto) {
        validateProductExists(productId);
        try {
            productRepository.updateProductById(productId, productReqDto);
        } catch (Exception e) {
            throw ProductUpdateException.EXCEPTION;
        }
    }

    public void deleteProduct(Long productId) {
        validateProductExists(productId);
        try {
            productRepository.deleteProductById(productId);
        } catch (Exception e) {
            throw ProductDeleteException.EXCEPTION;
        }
    }

    private void validateProductExists(Long productId) {
        boolean isExist = productRepository.isProductExistById(productId);

        if (!isExist) {
            throw ProductNotFoundException.EXCEPTION;
        }
    }
}
