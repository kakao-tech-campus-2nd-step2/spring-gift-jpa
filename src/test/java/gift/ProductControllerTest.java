package gift;

import gift.controller.ProductController;
import gift.model.Product;
import gift.service.ProductService;
import gift.service.MemberService;
import gift.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @MockBean
  private JwtUtil jwtUtil;

  @MockBean
  private MemberService memberService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testGetAllProducts() throws Exception {
    Product product1 = new Product("Product 1", 1000, "http://example.com/product1.jpg");
    product1.setId(1L);
    Product product2 = new Product("Product 2", 2000, "http://example.com/product2.jpg");
    product2.setId(2L);

    when(productService.findAll()).thenReturn(Arrays.asList(product1, product2));

    mockMvc.perform(get("/api/products")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].name").value("Product 1"))
            .andExpect(jsonPath("$[0].price").value(1000))
            .andExpect(jsonPath("$[0].imageUrl").value("http://example.com/product1.jpg"))
            .andExpect(jsonPath("$[1].id").value(2L))
            .andExpect(jsonPath("$[1].name").value("Product 2"))
            .andExpect(jsonPath("$[1].price").value(2000))
            .andExpect(jsonPath("$[1].imageUrl").value("http://example.com/product2.jpg"));

    verify(productService, times(1)).findAll();
  }

  @Test
  public void testGetProductById() throws Exception {
    Product product = new Product("Product 1", 1000, "http://example.com/product1.jpg");
    product.setId(1L);

    when(productService.findById(1L)).thenReturn(Optional.of(product));

    mockMvc.perform(get("/api/products/1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Product 1"))
            .andExpect(jsonPath("$.price").value(1000))
            .andExpect(jsonPath("$.imageUrl").value("http://example.com/product1.jpg"));

    verify(productService, times(1)).findById(1L);
  }

  @Test
  public void testCreateProduct() throws Exception {
    Product product = new Product("Product 1", 1000, "http://example.com/product1.jpg");
    Product savedProduct = new Product("Product 1", 1000, "http://example.com/product1.jpg");
    savedProduct.setId(1L);

    when(productService.save(any(Product.class))).thenReturn(savedProduct);

    mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(product)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Product 1"))
            .andExpect(jsonPath("$.price").value(1000))
            .andExpect(jsonPath("$.imageUrl").value("http://example.com/product1.jpg"));

    verify(productService, times(1)).save(any(Product.class));
  }

  @Test
  public void testDeleteProduct() throws Exception {
    Product product = new Product("Product 1", 1000, "http://example.com/product1.jpg");
    product.setId(1L);

    when(productService.findById(1L)).thenReturn(Optional.of(product));
    doNothing().when(productService).deleteById(1L);

    mockMvc.perform(delete("/api/products/1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent());

    verify(productService, times(1)).findById(1L);
    verify(productService, times(1)).deleteById(1L);
  }
}
