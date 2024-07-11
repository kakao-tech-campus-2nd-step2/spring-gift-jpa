@Controller
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product.Builder().build());
        return "addProduct";
    }

    @PostMapping
    public String addProduct(@Valid @ModelAttribute("product") ProductDTO productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addProduct";
        }
        try {
            Product product = new Product.Builder()
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .imageUrl(productDto.getImageUrl())
                    .build();
            productService.addProduct(product);
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "addProduct";
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid product data");
        }
        try {
            Product updatedProduct = new Product.Builder()
                    .id(id)
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .imageUrl(productDto.getImageUrl())
                    .build();
            productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok("Product updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
