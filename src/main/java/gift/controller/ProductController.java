package gift.controller;

import gift.domain.Product;
import gift.dto.ProductRequestDTO;
import gift.dto.ProductUpdateRequestDTO;
import gift.exception.ProductErrorCode;
import gift.exception.ProductException;
import gift.repository.ProductJdbcRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProductController {

    private final ProductJdbcRepository productjdbcRepository;

    @Autowired
    public ProductController(ProductJdbcRepository productRepository) {
        this.productjdbcRepository = productRepository;
    }


    // 상품 모두 조회
    @GetMapping("/api/products")
    public String responseAllProducts(Model model){
        List<Product> productsList = productjdbcRepository.findAll();
        model.addAttribute("products", productsList);
        return "index";
    }


    // 상품 추가 폼
    @GetMapping("/api/products/new-form")
    public String newProductForm(Model model) {
        model.addAttribute("productRequestDTO", new ProductRequestDTO());
        return "new-product-form";
    }

    // 상품 추가
    @PostMapping("/api/products")
    public String addOneProduct(@Valid @ModelAttribute ProductRequestDTO requestDTO, BindingResult bindingResult, Model model) {
        if (requestDTO.getName() != null && requestDTO.getName().contains("카카오")) {
            bindingResult.rejectValue("name", "error.name", "카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("productRequestDTO", requestDTO);
            model.addAttribute("org.springframework.validation.BindingResult.productRequestDTO", bindingResult);
            return "new-product-form";
        }
        productjdbcRepository.save(Product.fromEntity(requestDTO));
        return "redirect:/api/products";
    }

    // 상품 수정 폼
    @GetMapping("/api/products/edit/{id}")
    public String editProductForm(@PathVariable("id") long id, Model model) {
        Product product = productjdbcRepository.findById(id)
            .orElseThrow(() -> new ProductException(ProductErrorCode.ID_NOT_EXISTS));

        if (product != null) {
            model.addAttribute("product", product);
            return "modify-product-form";
        }
        return "redirect:/api/products";
    }

    // 상품 수정
    @PutMapping("/api/products/modify/{id}")
    public String modifyOneProduct(@PathVariable("id") long id, @Valid @ModelAttribute ProductUpdateRequestDTO requestDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", requestDTO);
            model.addAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            return "modify-product-form";
        }
        productjdbcRepository.update(requestDTO);
        return "redirect:/api/products";
    }

    // 상품 삭제
    @DeleteMapping("/api/products/{id}")
    public String deleteOneProduct(@PathVariable("id") long id) {
        productjdbcRepository.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.ID_NOT_EXISTS));

        productjdbcRepository.deleteById(id);
        return "index";
    }

    // 선택된 상품 삭제
    @DeleteMapping("/api/products/delete-selected")
    public ResponseEntity<String> deleteSelectedProducts(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            productjdbcRepository.findById(id)
                .orElseThrow(() -> new ProductException(ProductErrorCode.ID_NOT_EXISTS));

            productjdbcRepository.deleteById(id);
        }
        return new ResponseEntity<>("Selected products deleted successfully.", HttpStatus.OK);
    }
}
