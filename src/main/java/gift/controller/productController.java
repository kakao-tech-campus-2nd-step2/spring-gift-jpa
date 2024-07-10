package gift.controller;

import gift.dto.CreateProduct;
import gift.dto.EditProduct;
import gift.dto.ProductDto;
import gift.entity.Product;
import gift.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product/jdbc")
@RestController()
public class productController {

    private final ProductService productService;

    public productController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ProductDto createProduct(@RequestBody CreateProduct.Request request) {

        Product newProduct = productService.createProduct(
                new ProductDto(
                        request.getName(),
                        request.getPrice(),
                        request.getUrl()
                )
        );

        return new ProductDto(
                newProduct.getId(),
                newProduct.getName(),
                newProduct.getPrice(),
                newProduct.getUrl()
        );
    }

    @GetMapping("")
    public List<ProductDto> getAll() {

        return productService.getAll().stream().map(ProductDto::fromEntity).toList();

    }

    @GetMapping("/{id}")
    public ProductDto getOneById(@PathVariable("id") Long id) {

        Product product = productService.getOneById(id);

        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getUrl());

    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody EditProduct.Request request) {
        productService.update(id, new ProductDto(request.getName(), request.getPrice(), request.getUrl()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

}
