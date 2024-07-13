package gift.controller;

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
    public ProductDto.Request createProduct(@RequestBody ProductDto.Request request) {

        Product newProduct = productService.createProduct(request.getName(),request.getPrice(),request.getUrl());

        return new ProductDto.Request(
                newProduct.getId(),
                newProduct.getName(),
                newProduct.getPrice(),
                newProduct.getUrl()
        );
    }

    @GetMapping("")
    public List<ProductDto.Response> getAll() {
        return productService.getAll().stream().map(ProductDto.Response::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public ProductDto.Response getOneById(@PathVariable("id") Long id) {
        Product product = productService.getOneById(id);
        return new ProductDto.Response(product.getId(), product.getName(), product.getPrice(), product.getUrl());
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody ProductDto.Request request) {
        productService.update(id, request.getName(), request.getPrice(), request.getUrl());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @GetMapping("/{name}")
    public ProductDto.Response getOneByName(@PathVariable("name") String name) {
        return ProductDto.Response.fromEntity(productService.findProductByName(name));
    }
}
