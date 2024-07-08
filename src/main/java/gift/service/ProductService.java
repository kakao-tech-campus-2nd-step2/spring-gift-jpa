package gift.service;

import gift.exception.RepositoryException;
import gift.model.Product;
import gift.model.ProductDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductDTO productDTO) {
        productRepository.createProduct(productDTO);
    }

    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.getAllProduct();
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public ProductDTO getProductById(long id) {
        Product product = productRepository.getProductById(id);
        if (product == null) {
            throw new RepositoryException("데이터 베이스에서 해당 상품을 찾을 수 없습니다.");
        }
        return convertToDTO(product);

    }

    public void updateProduct(long id, ProductDTO productDTO) {
        if (!productRepository.updateProduct(id, productDTO)) {
            throw new RepositoryException("데이터 베이스에서 해당 상품을 찾을 수 없어 상품 정보를 업데이트 할 수 없습니다.");
        }
    }

    public String deleteProduct(long id) {
        if (!productRepository.deleteProduct(id)) {
            String error = "데이터 베이스에서 해당 상품을 찾을 수 없어 삭제할 수 없습니다.";
            throw new RepositoryException(error);
        }
        return "성공적으로 삭제되었습니다.";
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(),
            product.getPrice(), product.getImageUrl());
    }
}
