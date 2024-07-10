package gift.Service;

import gift.Model.Product;
import gift.Model.RequestProduct;
import gift.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        List<Product> list = productRepository.selectProduct();
        return list;
    }

    public void addProduct(RequestProduct requestProduct) {
        Product product = new Product(requestProduct.name(), requestProduct.price(), requestProduct.imageUrl());
        productRepository.insertProduct(product);
    }

    public Product selectProduct(long id) {
        Product product = productRepository.selectProductById(id);
        return product;
    }

    public void editProduct(long id, RequestProduct requestProduct) {
        Product product = new Product(id, requestProduct.name(), requestProduct.price(), requestProduct.imageUrl());
        productRepository.updateProduct(id, product);
    }

    public void deleteProduct(long id) {
        productRepository.deleteProduct(id);
    }


}
