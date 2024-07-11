package gift.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("selenium")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class AddPageTest {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static final Long SLEEP_TIME = 500L;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    @DisplayName("add product E2E Test")
    public void addPageTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/admin?page=1");

        for (int i = 0; i < 10; i++) {
            addProduct("Product-" + i, i + "000", "imageUrl-" + i);
        }

        String currentURL = driver.getCurrentUrl();
        assertEquals(currentURL, "http://localhost:" + port + "/admin?page=1");
    }

    private void addProduct(String name, String price, String imageUrl)
        throws InterruptedException {
        driver.findElement(By.className("header-add")).click();
        driver.findElement(By.id("productName")).sendKeys(name);
        driver.findElement(By.id("productPrice")).sendKeys(price);
        driver.findElement(By.id("productImageUrl")).sendKeys(imageUrl);
        driver.findElement(By.id("product-form")).findElement(By.tagName("button")).click();
        Thread.sleep(SLEEP_TIME);
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}