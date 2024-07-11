package gift.service;

import gift.dto.ProductDTO;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        return convertToDTO(product);
    }

    @Override
    public void saveProduct(ProductDTO productDTO) {

        Product product = convertToEntity(productDTO);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long id, ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        product.setId(id);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setImageUrl(product.getImageUrl());
        return productDTO;
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        return product;
    }

    @Override
    public Page<ProductDTO> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(this::convertToDTO);
    }

}

