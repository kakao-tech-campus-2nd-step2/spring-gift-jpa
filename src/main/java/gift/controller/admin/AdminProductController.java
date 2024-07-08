package gift.controller.admin;


import gift.domain.product.Product;
import gift.domain.product.ProductRequestDTO;
import gift.service.product.ProductService;
import gift.util.ImageStorageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductRequestDTO productRequestDTO,
                             @RequestPart MultipartFile imageFile) throws IOException {

        // Image storage and URL encoding
        String imagePath = ImageStorageUtil.saveImage(imageFile);
        String imageUrl = ImageStorageUtil.encodeImagePathToBase64(imagePath);

        // Create and save the product
        Product product = new Product(null, productRequestDTO.getName(), productRequestDTO.getPrice(),
                productRequestDTO.getDescription(), imageUrl);
        productService.addProduct(product);

        return "redirect:/admin/products";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute ProductRequestDTO productRequestDTO,
                                @RequestPart MultipartFile imageFile) throws IOException {

        Product product = productService.getProductById(id);

        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            ImageStorageUtil.deleteImage(ImageStorageUtil.decodeBase64ImagePath(product.getImageUrl()));
        }

        String imagePath = ImageStorageUtil.saveImage(imageFile);
        String imageUrl = ImageStorageUtil.encodeImagePathToBase64(imagePath);


        product.updateAdmin(productRequestDTO, imageUrl);
        productService.updateProduct(product);

        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null && product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            ImageStorageUtil.deleteImage(ImageStorageUtil.decodeBase64ImagePath(product.getImageUrl()));
        }
        productService.deleteProduct(id);

        return "redirect:/admin/products";
    }
}