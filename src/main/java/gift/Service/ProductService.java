package gift.Service;

import gift.Model.Product;
import gift.Model.RequestProduct;
import gift.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void addProduct(RequestProduct requestProduct) {
        Product product = new Product(requestProduct.name(), requestProduct.price(), requestProduct.imageUrl());
        productRepository.save(product);
    }

    public Optional<Product> selectProduct(long id) {
        Optional<Product> product = productRepository.findById(id);
        return product;
    }

    @Transactional
    public void editProduct(long id, RequestProduct requestProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.get();
        product.setName(requestProduct.name());
        product.setPrice(requestProduct.price());
        product.setImageUrl(requestProduct.imageUrl());
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }


}
