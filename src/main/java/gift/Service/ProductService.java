package gift.Service;

import gift.Model.Product;
import gift.Model.RequestProduct;
import gift.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProducts(int page, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage;
    }

    @Transactional
    public void addProduct(RequestProduct requestProduct) {
        Product product = new Product(requestProduct.name(), requestProduct.price(), requestProduct.imageUrl());
        productRepository.save(product);
    }

    public Product selectProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("매칭되는 product가 없습니다"));
        return product;
    }

    @Transactional
    public void editProduct(long id, RequestProduct requestProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("매칭되는 product가 없습니다"));
        product.setName(requestProduct.name());
        product.setPrice(requestProduct.price());
        product.setImageUrl(requestProduct.imageUrl());
    }

    @Transactional
    public void deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("매칭되는 product가 없습니다"));
        productRepository.deleteById(product.getId());
    }


}
