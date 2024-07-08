package gift.controller.product;

import gift.domain.product.Product;
import gift.domain.product.ProductRequestDTO;
import gift.service.product.ProductService;
import gift.util.ImageStorageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 모든 상품 조회
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // 특정 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    // 상품 추가
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        Product product = new Product(null, productRequestDTO.getName(), productRequestDTO.getPrice(),
                productRequestDTO.getDescription(), productRequestDTO.getImageUrl());  // 이미지 URL은 초기에는 null
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        // 이미지 URL 업데이트는 별도 API 사용
        product.update(productRequestDTO);

        productService.updateProduct(product);

        return ResponseEntity.ok(product);
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        // 기존 이미지 삭제는 별도 API 사용

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    //이미지 업로드 (사용자가 이미지를 업로드하고 반환되는 url을 받아 product 객체에 함께 담아서 보냄)
    @PostMapping("/imageUpload")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        // 새 이미지 저장 및 URL 업데이트
        String imagePath = ImageStorageUtil.saveImage(imageFile);
        String imageUrl = ImageStorageUtil.encodeImagePathToBase64(imagePath);

        return ResponseEntity.ok(imageUrl);
    }

    // 이미지 업데이트
    @PostMapping("/{id}/imageUpdate")
    public ResponseEntity<Product> updateProductImage(@PathVariable Long id,
                                                      @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        // 기존 이미지 삭제
        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            ImageStorageUtil.deleteImage(ImageStorageUtil.decodeBase64ImagePath(product.getImageUrl()));
        }

        // 새 이미지 저장 및 URL 업데이트
        String imagePath = ImageStorageUtil.saveImage(imageFile);
        String imageUrl = ImageStorageUtil.encodeImagePathToBase64(imagePath);
        product.setImageUrl(imageUrl);

        productService.updateProduct(product);

        return ResponseEntity.ok(product);
    }



    // 이미지 조회
    @GetMapping(value = "/{base64EncodedPath}/imageView", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageByEncodedPath(@PathVariable String base64EncodedPath) throws IOException {
        // 이미지 경로 디코딩
        System.out.println(base64EncodedPath);
        String imagePath = ImageStorageUtil.decodeBase64ImagePath(base64EncodedPath);

        // 이미지 바이트 전환
        byte[] imageBytes = java.nio.file.Files.readAllBytes(new File(imagePath).toPath());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }


}
