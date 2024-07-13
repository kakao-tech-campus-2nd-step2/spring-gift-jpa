package gift.user.controller;

import gift.product.dto.ProductDto;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {

  private final ProductService productService;

  @Autowired
  public AdminController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public String showAllProducts(Model model) {
    model.addAttribute("products", productService.findAll());
    return "product-list";
  }

  @GetMapping("/add")
  public String showAddProductForm(Model model) {
    model.addAttribute("product", new ProductDto());
    return "product-form";
  }

  @GetMapping("/edit/{id}")
  public String showEditProductForm(@PathVariable("id") Long id, Model model) {
    Optional<ProductDto> productDto = productService.getProductById(id);
    if (productDto.isPresent()) {
      model.addAttribute("product", productDto.get());
      return "product-form";
    } else {
      return "redirect:/admin";
    }
  }

  @PostMapping("/save")
  public String saveProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "product-form";
    }
    if (productDto.getId() == null) {
      productService.addProduct(productDto);
    } else {
      productService.updateProduct(productDto.getId(), productDto);
    }
    return "redirect:/admin";
  }

  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable("id") Long id) {
    productService.deleteProduct(id);
    return "redirect:/admin";
  }

  @DeleteMapping("/{id}")
  @ResponseBody
  public String deleteProductAjax(@PathVariable("id") Long id) {
    productService.deleteProduct(id);
    return "success";
  }
}
