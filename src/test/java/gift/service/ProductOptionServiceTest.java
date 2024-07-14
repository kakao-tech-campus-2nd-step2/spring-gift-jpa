package gift.service;

import gift.dto.ProductOptionRequest;
import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.model.MemberRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductOptionServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductOptionService optionService;
    private ProductResponse product;

    @BeforeEach
    @DisplayName("옵션에 대한 작업을 수행하기 위한 상품 추가 작업")
    void setBaseData() {
        var productRequest = new ProductRequest("아이폰16pro", 1800000, "https://image.zdnet.co.kr/2024/03/21/29acda4f841885d2122750fbff5cbd9d.jpg");
        product = productService.addProduct(productRequest, MemberRole.MEMBER);
    }

    @AfterEach
    @DisplayName("다른 테스트에서 생성된 상품 옵션을 삭제하기 위한 작업")
    void deleteBaseData() {
        productService.deleteProduct(product.id());
    }

    @Test
    @DisplayName("정상 옵션 추가하기")
    void successOptionAdd() {
        //given
        var productOptionRequest = new ProductOptionRequest(product.id(), "기본", 0);
        //when
        var savedOption = optionService.addOption(productOptionRequest);
        //then
        var findOption = optionService.getOption(savedOption.id());
        Assertions.assertThat(findOption.name()).isEqualTo("기본");
    }

    @Test
    @DisplayName("둘 이상의 옵션 추가하기")
    void addOptions() {
        //given
        var normalOptionDto = new ProductOptionRequest(product.id(), "기본", 0);
        var size255gbOptionDto = new ProductOptionRequest(product.id(), "255gb", 100000);
        //when
        optionService.addOption(normalOptionDto);
        optionService.addOption(size255gbOptionDto);
        //then
        Assertions.assertThat(optionService.getOptions(product.id(), PageRequest.of(0, 10)).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("옵션 수정하기")
    void updateOption() {
        //given
        var productOptionRequest = new ProductOptionRequest(product.id(), "기본", 0);
        var savedOption = optionService.addOption(productOptionRequest);
        var optionUpdateDto = new ProductOptionRequest(product.id(), "노멀", 0);
        //when
        optionService.updateOption(savedOption.id(), optionUpdateDto);
        //then
        var findOption = optionService.getOption(savedOption.id());
        Assertions.assertThat(findOption.name()).isNotEqualTo("기본");
        Assertions.assertThat(findOption.name()).isEqualTo("노멀");
    }

    @Test
    @DisplayName("옵션 삭제하기")
    void deleteOption() {
        //given
        var productOptionRequest = new ProductOptionRequest(product.id(), "기본", 0);
        var savedOption = optionService.addOption(productOptionRequest);
        Assertions.assertThat(optionService.getOptions(product.id(), PageRequest.of(0, 10)).size()).isEqualTo(1);
        //when
        optionService.deleteOption(savedOption.id());
        //then
        Assertions.assertThat(optionService.getOptions(product.id(), PageRequest.of(0, 10)).size()).isEqualTo(0);
    }
}
