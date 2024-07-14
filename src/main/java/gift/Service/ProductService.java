package gift.Service;

import gift.Model.Product;
import gift.Model.RequestProduct;
import gift.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        List<Product> list = productRepository.findAll();
        return list;
    }

    @Transactional
    public void addProduct(RequestProduct requestProduct) {
        Product product = new Product(requestProduct.name(), requestProduct.price(), requestProduct.imageUrl());
        productRepository.save(product);
    }

    public Product selectProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(()->new NoSuchElementException("매칭되는 product가 없습니다"));
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
