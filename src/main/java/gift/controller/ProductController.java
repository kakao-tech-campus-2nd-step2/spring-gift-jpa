package gift.controller;

import gift.domain.Product;
import gift.dto.ProductRequestDTO;
import gift.dto.ProductResponseDTO;
import gift.repository.ProductRepository;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public String getProducts(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "name,asc") String[] sort) {
        Page<Product> productPage = productService.getProducts(page, size, sort);

        List<ProductResponseDTO> productResponseDTOList = productPage.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDTO> productResponseDTOPage = new PageImpl<>(productResponseDTOList, pageable, productPage.getTotalElements());

        model.addAttribute("productPage", productResponseDTOPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "products";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("productRequestDTO", new ProductRequestDTO());
        return "productForm";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute("productRequestDTO") ProductRequestDTO productRequestDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "productForm";
        }

        if (productRequestDTO.getName().contains("카카오")) {
            model.addAttribute("errorMessage", "담당 MD와 협의된 경우에만 '카카오'를 포함할 수 있습니다.");
            return "productForm";
        }

        Product product = new Product.Builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .imageUrl(productRequestDTO.getImageUrl())
                .build();

        productRepository.save(product);
        return "redirect:/api/products"; // 리디렉션 설정
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));

        ProductRequestDTO productRequestDTO = convertToRequestDTO(product);
        model.addAttribute("productRequestDTO", productRequestDTO);
        return "productForm";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("productRequestDTO") ProductRequestDTO updatedProductDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "productForm";
        }

        if (updatedProductDTO.getName().contains("카카오")) {
            model.addAttribute("errorMessage", "담당 MD와 협의된 경우에만 '카카오'를 포함할 수 있습니다.");
            return "productForm";
        }

        Product updatedProduct = new Product.Builder()
                .id(id)
                .name(updatedProductDTO.getName())
                .price(updatedProductDTO.getPrice())
                .imageUrl(updatedProductDTO.getImageUrl())
                .build();

        productRepository.save(updatedProduct);
        return "redirect:/api/products"; // 리디렉션 설정
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/api/products"; // 리디렉션 설정
    }

    private ProductRequestDTO convertToRequestDTO(Product product) {
        return new ProductRequestDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

    private ProductResponseDTO convertToResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setImageUrl(product.getImageUrl());
        return productResponseDTO;
    }
}
