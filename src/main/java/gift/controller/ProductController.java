package gift.controller;

import gift.model.Product;
import gift.dao.ProductDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ProductController {
    private final ProductDao productDao;
    private final CatchError catchError = new CatchError();

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    // 모든 상품 반환
    @GetMapping("/getAllProducts")
    public String getProductsController(Model model) {
        List<Product> productList = productDao.selectAllProduct();
        model.addAttribute("productList", productList);
        return "index";
    }

    @GetMapping("/getAllProductList")
    @ResponseBody
    public List<Product> getProductsListController() {
        return productDao.selectAllProduct();
    }

    // id 상품 하나 반환
    @GetMapping("/getProduct/{id}")
    @ResponseBody
    public Product getProductByIdController(@PathVariable Long id) {
        return productDao.selectProduct(id);
    }

    // 상품 추가
    @PostMapping("/postProduct")
    public String postProductController(@ModelAttribute Product product) {
        System.out.println(product);
        if (!catchError.isCorrectName(product.getName())) {
            throw new IllegalArgumentException("이름은 15자 이내, 특수문자는 (),[],+,-,&,/,_ 만 사용 가능합니다.");
        }
        if (catchError.isContainsKakao(product.getName())) {
            throw new IllegalArgumentException("\"카카오\"는 MD와 협의 후에 사용 가능합니다.");
        }
        // Product 객체 생성 시 id 제외
        productDao.insertProduct(product);
        return "redirect:/api/getAllProducts";
    }

    // 상품 삭제
    @GetMapping("/deleteProduct/{id}")
    public String deleteProductController(@PathVariable Long id) {
        productDao.deleteProduct(id);
        return "redirect:/api/getAllProducts";
    }

    // 상품 업데이트
    @PostMapping("/updateProduct/{id}")
    public String updateProductController(@PathVariable Long id, @ModelAttribute Product newProduct) {
        Product oldProduct = productDao.selectProduct(id);

        if (!catchError.isCorrectName(newProduct.getName())) {
            throw new IllegalArgumentException("이름은 15자 이내, 특수문자는 (),[],+,-,&,/,_ 만 사용 가능합니다.");
        }
        if (catchError.isContainsKakao(newProduct.getName())) {
            throw new IllegalArgumentException("\"카카오\"는 MD와 협의 후에 사용 가능합니다.");
        }

        Product updatedProduct = new Product(
                oldProduct.getId(),
                newProduct.getName() != null && !newProduct.getName().isEmpty() ? newProduct.getName() : oldProduct.getName(),
                newProduct.getPrice() != null ? newProduct.getPrice() : oldProduct.getPrice(),
                newProduct.getImageUrl() != null && !newProduct.getImageUrl().isEmpty() ? newProduct.getImageUrl() : oldProduct.getImageUrl()
        );

        productDao.updateProduct(updatedProduct);
        return "redirect:/api/getAllProducts";
    }

    // 이름 오류 발생시 이유 리턴
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "errorPage";
    }
}