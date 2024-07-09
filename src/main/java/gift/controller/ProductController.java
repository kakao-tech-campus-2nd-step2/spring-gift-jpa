package gift.controller;

import static gift.util.ResponseEntityUtil.responseError;

import gift.constants.ResponseMsgConstants;
import gift.dto.ProductDTO;
import gift.dto.ResponseDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Validated
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String getProducts(Model model) {
        model.addAttribute("productList", productService.getProductList());
        return "getProducts";
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO> addProduct(@RequestBody @Valid ProductDTO productDTO) {
        try {
            productService.addProduct(productDTO);
        } catch (RuntimeException e) {
            return responseError(e);
        }
        return new ResponseEntity<>(new ResponseDTO(false, ResponseMsgConstants.WELL_DONE_MESSAGE),
                HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable @Min(1) @NotNull Integer id) {
        try {
            productService.deleteProduct(id);
        } catch (RuntimeException e) {
            return responseError(e);
        }
        return new ResponseEntity<>(new ResponseDTO(false, ResponseMsgConstants.WELL_DONE_MESSAGE),
                HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateProduct(@PathVariable @Min(1) @NotNull Integer id,
            @RequestBody @Valid ProductDTO productDTO) {
        try {
            productService.updateProduct(id, productDTO);

        } catch (RuntimeException e) {
            return responseError(e);
        }
        return new ResponseEntity<>(new ResponseDTO(false, ResponseMsgConstants.WELL_DONE_MESSAGE),
                HttpStatus.OK);
    }




}
