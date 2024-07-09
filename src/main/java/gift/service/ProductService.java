package gift.service;

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

    public List<ProductDTO> findAll() {
        return productRepository.findAll()
            .stream()
            .map(product -> product.toDTO())
            .collect(Collectors.toList());
    }


    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(NoSuchProductException::new)
            .toDTO();
    }

    public ProductDTO save(ProductDTO productDTO) {
        return productRepository.save(productDTO.toEntity()).toDTO();
    }

    public ProductDTO update(long id, ProductDTO productDTO) {
        findById(id);
        ProductDTO updatedProductDTO = new ProductDTO(id, productDTO.name(), productDTO.price(), productDTO.imageUrl());
        return productRepository.save(updatedProductDTO.toEntity()).toDTO();
    }

    public ProductDTO delete(long id) {
        ProductDTO deletedProductDTO = findById(id);
        productRepository.deleteById(id);
        return deletedProductDTO;
    }
}
