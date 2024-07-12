package gift.service;

import gift.controller.dto.ProductDTO;
import gift.domain.Product;
import gift.repository.ProductRepository;
import gift.utils.error.NotpermitNameException;
import gift.utils.error.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GiftService {

    private final ProductRepository productRepository;

    public GiftService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).
            orElseThrow(() -> new ProductNotFoundException("Product NOT FOUND"));
    }

    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ProductDTO postProducts(ProductDTO productDTO) {
        validateProductName(productDTO.getName());

        Product product = new Product(productDTO.getName(),
            productDTO.getPrice(), productDTO.getImageUrl());

        Product savedProduct = productRepository.save(product);

        return new ProductDTO(savedProduct.getName(), savedProduct.getPrice(),
            savedProduct.getImageUrl());
    }

    public ProductDTO putProducts(ProductDTO productDTO, Long id) {
        validateProductName(productDTO.getName());

        Product productById = productRepository.findById(id).
            orElseThrow(() -> new ProductNotFoundException("Product NOT FOUND"));

        productById.setName(productDTO.getName());
        productById.setPrice(productDTO.getPrice());
        productById.setImageUrl(productDTO.getImageUrl());
        Product saveProduct = productRepository.save(productById);

        return new ProductDTO(saveProduct.getName(), saveProduct.getPrice(),
            saveProduct.getImageUrl());
    }

    public Long deleteProducts(Long id) {
        productRepository.findById(id).orElseThrow(
            () -> new ProductNotFoundException("Product NOT FOUND"));
        productRepository.deleteById(id);
        return id;
    }

    private void validateProductName(String name) {
        if (name.replace(" ", "").contains("카카오")) {
            throw new NotpermitNameException("\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있다.");
        }
    }

}
