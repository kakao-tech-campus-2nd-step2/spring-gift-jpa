package gift.Controller;

import gift.model.Product;
import gift.model.ProductDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ProductController {
    long id = 0L;

    private final ProductDao ProductDao;

    public ProductController(gift.model.ProductDao productDao) {
        ProductDao = productDao;
    }

    //모든 상품 반환
    @GetMapping("/getAllProducts")
    public String getProductsController(Model model) {
        List<Product> productList = ProductDao.selectAllProduct();
        model.addAttribute("productList", productList);
        return "index";
    }

    @GetMapping("/getAllProductList")
    @ResponseBody
    public List<Product> getProductsListController(Model model) {
        List<Product> productList = ProductDao.selectAllProduct();
        return productList;
    }

    //id 상품 하나 반환
    @GetMapping("/getProduct/{id}")
    @ResponseBody
    public Product getProductByIdController(@PathVariable Long id) {
        Product product = ProductDao.selectProduct(id);
        return product;
    }

    //상품 추가
    @PostMapping("/postProduct")
    public String postProductController(@ModelAttribute Product product) {
        if (!CatchError.isCorrectName(product.getName())) {
            throw new IllegalArgumentException("이름은 15자 이내, 특수문자는 (),[],+,-,&,/,_ 만 사용 가능합니다.");
        }
        if (CatchError.isContainsKakao(product.getName())) {
            throw new IllegalArgumentException("\"카카오\"는 MD와 협의 후에 사용 가능합니다.");
        }
        id++;
        product.setId(id);
        ProductDao.insertProduct(product);
        return "redirect:/api/getAllProducts";
    }

    //상품 삭제
    @GetMapping("/deleteProduct/{id}")
    public String deleteProductController(@PathVariable Long id) {
        ProductDao.deleteProduct(id);
        return "redirect:/api/getAllProducts";
    }

    //상품 업데이트
    @PostMapping("/updateProduct/{id}")
    public String updateProductController(@PathVariable Long id, @ModelAttribute Product newProduct) {
        Product oldProduct = ProductDao.selectProduct(id);

        if (!CatchError.isCorrectName(newProduct.getName())) {
            throw new IllegalArgumentException("이름은 15자 이내, 특수문자는 (),[],+,-,&,/,_ 만 사용 가능합니다.");
        }
        if (CatchError.isContainsKakao(newProduct.getName())) {
            throw new IllegalArgumentException("\"카카오\"는 MD와 협의 후에 사용 가능합니다.");
        }

        if (newProduct.getName() != null && !newProduct.getName().isEmpty()) {
            oldProduct.setName(newProduct.getName());
        }
        if (newProduct.getPrice() != null) {
            oldProduct.setPrice(newProduct.getPrice());
        }
        if (newProduct.getImageUrl() != null && !newProduct.getImageUrl().isEmpty()) {
            oldProduct.setImageUrl(newProduct.getImageUrl());
        }
        ProductDao.updateProduct(oldProduct);
        return "redirect:/api/getAllProducts";
    }

    //이름 오류 발생시 이유 리턴
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "errorPage";
    }
}