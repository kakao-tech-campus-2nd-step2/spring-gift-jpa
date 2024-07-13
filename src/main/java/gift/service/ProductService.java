package gift.service;

import gift.entity.Product;
import gift.domain.ProductDTO;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        Product product = new Product(1, "test", "imgURL");
        productRepository.save(product);
    }

    public Page<Product> getAllProduct(int page, int size) {
        Pageable pageRequest = createPageRequestUsing(page, size);

        int start = (int) pageRequest.getOffset();
        int end = start + pageRequest.getPageSize();
        if (page > 0) { start += 1; }

        List<Product> pageContent = productRepository.findByProductIdBetween(start, end);
        return new PageImpl<>(pageContent, pageRequest, pageContent.size());
    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    public Product getProductById(int id) {
        try {
            return productRepository.findById(id);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }

    public Product addProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO.price(), productDTO.name(), productDTO.imgURL());
        try {
            return productRepository.save(product);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public Product updateProduct(int id, ProductDTO productDTO) {
        Product product = new Product(id, productDTO.price(), productDTO.name(), productDTO.imgURL());
        try {
            return productRepository.save(product);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No product found with id " + id);
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void deleteProduct(int id) {
        try {
            productRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new NoSuchElementException();
        }
    }
}
