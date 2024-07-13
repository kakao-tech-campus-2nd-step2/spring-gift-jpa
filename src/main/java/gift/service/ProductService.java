package gift.service;

import gift.domain.Product;
import gift.repository.ProductRepository;
import gift.dto.ProductDTO;
import gift.exception.NoSuchProductException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getProducts() {
        return productRepository.findAll()
            .stream()
            .map(product -> product.toDTO())
            .collect(Collectors.toList());
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
        ProductDTO deletedProductDTO = getProduct(id);
        productRepository.delete(deletedProductDTO.toEntity());
        return deletedProductDTO;
    }
}
