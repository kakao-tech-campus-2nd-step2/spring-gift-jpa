package gift.controller;

import gift.dto.ProductDTO;
import gift.model.Product;

import gift.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody ProductDTO newProductDTO) {
        productService.saveProduct(newProductDTO);
        return productService.toEntity(newProductDTO, null);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable(value = "id") long id) {
        return productService.findProductsById(id);
    }

    @GetMapping
    public Page<Product> getAllProduct(@PageableDefault(size = 5) Pageable pageable) {
        return productService.findAllProducts(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable(value = "id") long id) {
        productService.deleteProductAndWishlist(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") long id,
        @RequestBody ProductDTO updatedProductDTO) {
        productService.updateProduct(updatedProductDTO, id);
        return productService.findProductsById(id);
    }

    private static Product toEntity(Product product, Long id) {
        return new Product(id, product.getName(), product.getPrice(), product.getImageUrl());
    }
}
