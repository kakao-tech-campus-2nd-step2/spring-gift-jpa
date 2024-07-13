package gift.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getProductPages(int pageNum, int size, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Order.asc(sortBy)));
        if (Objects.equals(sortDirection, "desc")) {
            pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Order.desc(sortBy)));
        }

        return productRepository.findAll(pageable);
    }

    public Product findByID(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public Product insertNewProduct(ProductDTO newProduct) {
        Product product = new Product(newProduct.getName(), newProduct.getPrice(), newProduct.getImageUrl());
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductDTO updateProduct) {
        Product product = findByID(id);
        product.setName(updateProduct.getName());
        product.setPrice(updateProduct.getPrice());
        product.setImageUrl(updateProduct.getImageUrl());
        return product;
    }

    public void deleteProduct(Long id) {
        if (findByID(id) != null) productRepository.deleteById(id);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
