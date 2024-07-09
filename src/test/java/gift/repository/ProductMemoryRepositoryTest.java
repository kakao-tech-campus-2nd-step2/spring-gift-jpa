package gift.repository;

import gift.dto.request.ProductRequest;
import gift.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class ProductMemoryRepositoryTest {

    private ProductMemoryRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ProductMemoryRepository();
    }

    @Test
    void saveProduct() {
        Product product = new Product("Test Product", 100, "test-url");

        Product savedProduct = repository.save(product);

        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getImageUrl(), savedProduct.getImageUrl());
        assertTrue(savedProduct.getId() > 0);
    }

    @Test
    void findById_id있을때() {
        Product product = new Product("Test Product", 100, "test-url");
        Product savedProduct = repository.save(product);
        Long productId = savedProduct.getId();

        Optional<Product> foundProduct = repository.findById(productId);

        assertTrue(foundProduct.isPresent());
        assertEquals(savedProduct, foundProduct.get());
    }

    @Test
    void findById_id없을때() {
        Optional<Product> foundProduct = repository.findById(999L);

        assertFalse(foundProduct.isPresent());
    }

    @Test
    void findByName_name있을때() {
        Product product = new Product("Test Product", 100, "test-url");
        repository.save(product);
        String productName = product.getName();

        Optional<Product> foundProduct = repository.findByName(productName);

        assertTrue(foundProduct.isPresent());
        assertEquals(productName, foundProduct.get().getName());
    }

    @Test
    void findByName_name없을때() {
        Optional<Product> foundProduct = repository.findByName("Non Existing Product");

        assertFalse(foundProduct.isPresent());
    }

    @Test
    void findAll() {
        Product product1 = new Product("Product 1", 100, "url-1");
        Product product2 = new Product("Product 2", 200, "url-2");
        repository.save(product1);
        repository.save(product2);

        List<Product> allProducts = repository.findAll();

        assertEquals(2, allProducts.size());
    }

    @Test
    void updateById_id있을때() {
        Product product = new Product("Initial Product", 100, "initial-url");
        Product savedProduct = repository.save(product);
        Long productId = savedProduct.getId();
        ProductRequest updatedProductRequest = new ProductRequest("Updated Product", 200, "updated-url");

        Optional<Product> updatedProductOptional = repository.updateById(productId, updatedProductRequest);

        assertTrue(updatedProductOptional.isPresent());
        Product updatedProduct = updatedProductOptional.get();
        assertEquals(updatedProductRequest.getName(), updatedProduct.getName());
        assertEquals(updatedProductRequest.getPrice(), updatedProduct.getPrice());
        assertEquals(updatedProductRequest.getImageUrl(), updatedProduct.getImageUrl());
    }

    @Test
    void updateById_id없을때() {
        Optional<Product> updatedProductOptional = repository.updateById(999L, new ProductRequest("Updated Product", 200, "updated-url"));

        assertFalse(updatedProductOptional.isPresent());
    }

    @Test
    void deleteById_id있을때() {
        Product product = new Product("To Be Deleted", 100, "delete-me-url");
        Product savedProduct = repository.save(product);
        Long productId = savedProduct.getId();

        Optional<Product> deletedProductOptional = repository.deleteById(productId);

        assertTrue(deletedProductOptional.isPresent());
        assertEquals(product, deletedProductOptional.get());
        assertFalse(repository.findById(productId).isPresent());
    }

    @Test
    void deleteById_id없을때() {
        Optional<Product> deletedProductOptional = repository.deleteById(999L);

        assertFalse(deletedProductOptional.isPresent());
    }

    //동시성 테스트
    @Test
    public void testConcurrentProductCreation() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        Set<Long> productIds = ConcurrentHashMap.newKeySet();
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                Product product = new Product("Product", 100, "http://example.com/image.png");
                repository.save(product);
                productIds.add(product.getId());
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();

        // Check if there are any duplicate IDs
        assertEquals(numberOfThreads, productIds.size(), "Duplicates found! Number of unique IDs: " + productIds.size());
    }

}