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

    //insert
    @PostMapping("")
    public String createProduct(@RequestBody CreateProduct.Request request) {

        productService.createProduct(new ProductDto(request.getName(), request.getPrice(), request.getUrl()));
        return "product 가 생성되었습니다.";
    }

    // get all
    @GetMapping("")
    public List<Product> getAll() {
        return productService.getAll();
    }

    // get one by id
    @GetMapping("/{id}")
    public Product getOneById(@PathVariable("id") Long id) {
        return productService.getOneById(id);
    }

    //update
    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody EditProduct.Request request) {

        productService.update(id, new ProductDto(request.getName(), request.getPrice(), request.getUrl()));

    }

    //delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        productService.delete(id);
    }

}
