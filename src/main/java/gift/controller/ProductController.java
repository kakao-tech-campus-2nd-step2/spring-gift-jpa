package gift.controller;

import gift.dto.ProductResponseDto;
import gift.model.Product;
import gift.repository.ProductDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductDao productDao;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {

        List<Product> productsList = productDao.getAllProducts();
        if(productsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        List<ProductResponseDto> dtoList = productsList.stream()
            .map(ProductResponseDto::new)
            .toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {

        Product product = productDao.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        ProductResponseDto productResponseDto = new ProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PostMapping("/api/products")
    public void addProduct(@RequestBody ProductResponseDto productResponseDto) {
        Product product = new Product(productResponseDto.name(),
            productResponseDto.price(), productResponseDto.imageUrl());
        productDao.insertProduct(product);
    }

    @PutMapping("/api/products")
    public void updateProduct(@RequestBody ProductResponseDto productResponseDto) {
        Product updatedProduct = new Product(productResponseDto.id(), productResponseDto.name(),
            productResponseDto.price(), productResponseDto.imageUrl());
        productDao.updateProduct(updatedProduct);
    }

    @DeleteMapping("/api/products")
    public void deleteProduct(@RequestParam("id") Long id) {
        productDao.deleteProduct(id);
    }

}
