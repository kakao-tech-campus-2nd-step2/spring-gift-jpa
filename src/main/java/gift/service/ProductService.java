package gift.service;

import gift.exception.ErrorCode;
import gift.exception.RepositoryException;
import gift.model.Product;
import gift.model.ProductDTO;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO.id(), productDTO.name(), productDTO.price(),
            productDTO.imageUrl());
        return convertToDTO(productRepository.save(product));
    }

    public Page<ProductDTO> getAllProduct(int page, int size) {
        Pageable pageRequest = createPageRequestUsing(page, size);
        List<Product> allProducts = productRepository.findAll();
        List<ProductDTO> allProductDTO = allProducts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allProductDTO.size());

        List<ProductDTO> pageContent = allProductDTO.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, allProductDTO.size());
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    public List<ProductDTO> getAllProductByList() {
        List<Product> products = productRepository.findAll();
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public ProductDTO getProductById(long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RepositoryException(ErrorCode.PRODUCT_NOT_FOUND, ""));
        return convertToDTO(product);
    }

    public ProductDTO updateProduct(long id, ProductDTO productDTO) {
        Product product = new Product(id, productDTO.name(), productDTO.price(),
            productDTO.imageUrl());
        return convertToDTO(productRepository.save(product));
    }

    public String deleteProduct(long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RepositoryException(ErrorCode.PRODUCT_NOT_FOUND, "");
        }
        return "성공적으로 삭제되었습니다.";
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(),
            product.getPrice(), product.getImageUrl());
    }
}
