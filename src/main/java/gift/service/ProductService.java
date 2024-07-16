package gift.service;

import gift.domain.Product;
import gift.repository.ProductRepository;
import gift.util.AlphanumericComparator;
import java.util.Collections;
import java.util.Comparator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> findAllProducts(Pageable pageable) {
        List<Product> products = getSortedProducts();

        return paginate(products, pageable);
    }

    private List<Product> getSortedProducts() {
        List<Product> products = productRepository.findAll();
        products.sort(Comparator.comparing(Product::getName, new AlphanumericComparator()));
        return products;
    }

    private Page<Product> paginate(List<Product> products, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Product> paginatedList = getPaginatedList(products, startItem, pageSize);

        return new PageImpl<>(paginatedList, pageable, products.size());
    }

    private List<Product> getPaginatedList(List<Product> products, int startItem, int pageSize) {
        if (products.size() <= startItem) {
            return Collections.emptyList();
        }

        int toIndex = Math.min(startItem + pageSize, products.size());
        return products.subList(startItem, toIndex);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        if (!isExist(id)) {
            return createProduct(product);
        }
        Product updatedProduct = new Product.ProductBuilder()
            .id(id)
            .name(product.getName())
            .price(product.getPrice())
            .imageUrl(product.getImageUrl())
            .description(product.getDescription())
            .build();
        return productRepository.save(updatedProduct);
    }

    public Long deleteProduct(Long id) {
        if (!isExist(id)) {
            return -1L;
        }
        productRepository.deleteById(id);
        return id;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    private boolean isExist(Long id) {
        return productRepository.existsById(id);
    }
}
