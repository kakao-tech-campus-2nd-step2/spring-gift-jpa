package gift.service;

import gift.domain.Product;
import gift.repository.ProductRepository;
import gift.dto.ProductDTO;
import gift.exception.NoSuchProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductDTO> getProducts(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productRepository.findAll(pageable)
            .map(product -> product.toDTO());
    }

    public ProductDTO getProduct(Long id) {
        return productRepository.findById(id)
            .orElseThrow(NoSuchProductException::new)
            .toDTO();
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        return productRepository.save(productDTO.toEntity()).toDTO();
    }

    public ProductDTO updateProduct(long id, ProductDTO productDTO) {
        getProduct(id);
        Product product = new Product(id, productDTO.name(), productDTO.price(), productDTO.imageUrl());
        return productRepository.save(product).toDTO();
    }

    public ProductDTO deleteProduct(long id) {
        Product deletedProduct = productRepository.findById(id).orElseThrow(NoSuchProductException::new);
        productRepository.delete(deletedProduct);
        return deletedProduct.toDTO();
    }
}
