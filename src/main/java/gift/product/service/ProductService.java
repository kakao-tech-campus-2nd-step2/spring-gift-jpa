package gift.product.service;

import gift.product.dto.ProductDTO;
import gift.product.repository.ProductRepository;
import gift.product.model.Product;
import gift.product.validation.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidation productValidation;

    @Autowired
    public ProductService(
            ProductRepository productRepository,
            ProductValidation productValidation
    ) {
        this.productRepository = productRepository;
        this.productValidation = productValidation;
    }

    public ResponseEntity<String> registerProduct(ProductDTO productDTO) {
        System.out.println("[ProductService] registerProduct()");
        productValidation.registerValidation(productDTO);

        productRepository.save(
                new Product(
                        productDTO.getName(),
                        productDTO.getPrice(),
                        productDTO.getImageUrl()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("Product registered successfully");
    }

    public ResponseEntity<String> updateProduct(
            Long id,
            ProductDTO productDTO
    ) {
        System.out.println("[ProductService] updateProduct()");
        productValidation.updateValidation(id, productDTO);

        productRepository.save(
                new Product(
                        id,
                        productDTO.getName(),
                        productDTO.getPrice(),
                        productDTO.getImageUrl()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("Product update successfully");
    }

    public void deleteProduct(Long id) {
        productValidation.isExistId(id);
        productRepository.deleteById(id);
    }

    public ProductDTO getDTOById(Long id) {
        System.out.println("[ProductService] getDTOById()");

        productValidation.isExistId(id);

        Product product = productRepository.findById(id).get();
        return convertToDTO(product);
    }

    public Page<ProductDTO> searchProducts(
            String keyword,
            Pageable pageable
    ) {
        return convertToDTOList(
            productRepository.findByName(
                keyword,
                pageable
            ),
            pageable
        );
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return convertToDTOList(
            productRepository.findAll(pageable),
            pageable
        );
    }

    public ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

    public Page<ProductDTO> convertToDTOList(Page<Product> products, Pageable pageable) {
        List<ProductDTO> productDTOs = products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(
            productDTOs,
            pageable,
            products.getTotalElements()
        );
    }
}
