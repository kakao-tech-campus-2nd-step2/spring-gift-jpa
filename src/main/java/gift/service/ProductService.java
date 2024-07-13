package gift.service;

import gift.converter.ProductConverter;
import gift.dto.ProductDTO;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private static final int MAX_PAGE_SIZE = 30;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Pageable createPageRequest(int page, int size, String sortBy, String direction) {
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }

        Sort sort;
        if (direction.equalsIgnoreCase(Sort.Direction.DESC.name())) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        return PageRequest.of(page, size, sort);
    }

    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductConverter::convertToDTO);
    }

    public Long addProduct(ProductDTO productDTO) {
        Product product = ProductConverter.convertToEntity(productDTO);
        productRepository.save(product);
        return product.getId();
    }

    public Optional<ProductDTO> findProductById(Long id) {
        return productRepository.findById(id)
            .map(ProductConverter::convertToDTO);
    }

    public void updateProduct(ProductDTO productDTO) {
        Product product = ProductConverter.convertToEntity(productDTO);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}