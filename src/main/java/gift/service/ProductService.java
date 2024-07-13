package gift.service;

import gift.dto.product.ProductPatchDTO;
import gift.dto.product.ProductRequestDTO;
import gift.dto.product.ProductResponseDTO;
import gift.entity.Product;
import gift.exception.NoSuchProductException;
import gift.repository.ProductRepository;
import gift.util.pagenation.PageInfoDTO;
import gift.util.pagenation.PageableGenerator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::from)
                .toList();
    }

    public List<ProductResponseDTO> getAllProducts(PageInfoDTO pageInfoDTO) {
        Pageable pageable = PageableGenerator.generatePageable(pageInfoDTO);

        return productRepository.findAll(pageable)
                .stream()
                .map(ProductResponseDTO::from)
                .toList();
    }

    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Product product = productRepository.save(Product.from(productRequestDTO));

        return ProductResponseDTO.from(product);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public ProductResponseDTO updateProduct(long id, ProductPatchDTO patchDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(NoSuchProductException::new);

        Product patchedProduct = productRepository.save(product.patch(patchDTO));

        return ProductResponseDTO.from(patchedProduct);
    }

    public ProductResponseDTO getProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(NoSuchProductException::new);

        return ProductResponseDTO.from(product);
    }
}
