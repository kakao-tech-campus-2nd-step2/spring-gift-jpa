package gift.product;

import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductDTO> getAllProducts(int page, int size, String sortBy, Direction direction) {
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageRequest = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageRequest);
        List<ProductDTO> products = productPage.stream()
            .map(ProductDTO::fromProduct)
            .toList();
        return new PageImpl<>(products, pageRequest, productPage.getTotalElements());
    }

    public ProductDTO getProductById(long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        return ProductDTO.fromProduct(product);
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    public void addProduct(ProductDTO product) {
        if (productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
        productRepository.save(product.toProduct());
    }

    public void updateProduct(ProductDTO productDTO) throws NotFoundException {
        Product product = productRepository.findById(productDTO.getId())
            .orElseThrow(NotFoundException::new);
        if (productRepository.existsByName(productDTO.getName())
            && product.getId() != productDTO.getId()) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
        product.update(productDTO.getName(), productDTO.getPrice(), productDTO.getImageUrl());
        productRepository.save(product);
    }

    public void existsByNamePutResult(String name, BindingResult result) {
        if (existsByName(name)) {
            result.addError(new FieldError("productDTO", "name", "존재하는 이름입니다."));
        }
    }

    public void deleteProduct(long id) throws NotFoundException {
        productRepository.findById(id).orElseThrow(NotFoundException::new);
        productRepository.deleteById(id);
    }
}
