package gift.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.response.WishResponseDto;
import gift.exception.EntityNotFoundException;
import gift.exception.ForbiddenException;
import gift.repository.member.MemberRepository;
import gift.repository.product.ProductRepository;
import gift.repository.wish.WishRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class WishServiceTest {

    @InjectMocks
    private WishService wishService;

    @Mock
    private WishRepository wishRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("WISH 저장 테스트")
    void 위시_저장_테스트(){
        //given
        Long productId = 1L;
        Long nullProductId = 2L;
        Product product = new Product.Builder()
                .name("테스트 상품")
                .price(1000)
                .imageUrl("abc.png")
                .build();

        String email = "abc@pusan.ac.kr";
        String nullEmail = "abcd@pusan.ac.kr";
        Member member = new Member.Builder()
                .email(email)
                .password("abc")
                .build();

        Wish wish = new Wish.Builder()
                .member(member)
                .product(product)
                .count(100)
                .build();

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(productRepository.findById(nullProductId)).willReturn(Optional.empty());
        given(memberRepository.findMemberByEmail(email)).willReturn(Optional.of(member));
        given(memberRepository.findMemberByEmail(nullEmail)).willReturn(Optional.empty());
        given(wishRepository.save(any(Wish.class))).willReturn(wish);

        //when
        WishResponseDto wishResponseDto = wishService.addWish(productId, email, 100);

        //then
        assertAll(
                () -> assertThat(wishResponseDto.count()).isEqualTo(100),
                () -> assertThat(wishResponseDto.productResponseDto().name()).isEqualTo(product.getName()),
                () -> assertThat(wishResponseDto.productResponseDto().price()).isEqualTo(product.getPrice()),
                () -> assertThat(wishResponseDto.productResponseDto().imageUrl()).isEqualTo(product.getImageUrl()),
                () -> assertThatThrownBy(() -> wishService.addWish(nullProductId, email, 100))
                        .isInstanceOf(EntityNotFoundException.class)
                        .hasMessage("존재 하지 않는 상품입니다."),
                () -> assertThatThrownBy(() -> wishService.addWish(productId, nullEmail, 100))
                        .isInstanceOf(EntityNotFoundException.class)
                        .hasMessage("존재 하지 않는 회원 입니다.")
        );
    }

    @Test
    @DisplayName("WISH 수정 테스트")
    void 위시_수정_테스트(){
        //given
        Long validId = 1L;
        Long inValidId = 2L;

        String validEmail = "abc@pusan.ac.kr";
        String inValidEmail = "abcd@pusan.ac.kr";

        Product product = new Product.Builder()
                .name("테스트 상품")
                .price(1000)
                .imageUrl("abc.png")
                .build();

        Member member = new Member.Builder()
                .email(validEmail)
                .password("abc")
                .build();

        Wish wish = new Wish.Builder()
                .member(member)
                .product(product)
                .count(100)
                .build();

        given(wishRepository.findById(validId)).willReturn(Optional.of(new Wish()));
        given(wishRepository.findById(inValidId)).willReturn(Optional.empty());
        given(wishRepository.findWishByIdAndMemberEmail(validId, validEmail)).willReturn(Optional.of(wish));
        given(wishRepository.findWishByIdAndMemberEmail(validId, inValidEmail)).willReturn(Optional.empty());

        //when
        WishResponseDto wishResponseDto = wishService.editWish(validId, validEmail, 1000);

        //then
        assertAll(
                () -> assertThat(wishResponseDto.count()).isEqualTo(1000),
                () -> assertThat(wishResponseDto.productResponseDto().name()).isEqualTo(product.getName()),
                () -> assertThat(wishResponseDto.productResponseDto().price()).isEqualTo(product.getPrice()),
                () -> assertThat(wishResponseDto.productResponseDto().imageUrl()).isEqualTo(product.getImageUrl()),
                () -> assertThatThrownBy(() -> wishService.editWish(inValidId, validEmail, 100))
                        .isInstanceOf(EntityNotFoundException.class)
                        .hasMessage("해당 WISH가 존재하지 않습니다"),
                () -> assertThatThrownBy(() -> wishService.editWish(validId, inValidEmail, 100))
                        .isInstanceOf(ForbiddenException.class)
        );
    }

    @Test
    @DisplayName("WISH 삭제 테스트")
    void 위시_삭제_테스트(){
        //given
        Long validId = 1L;
        Long inValidId = 2L;

        String validEmail = "abc@pusan.ac.kr";
        String inValidEmail = "abcd@pusan.ac.kr";

        Product product = new Product.Builder()
                .name("테스트 상품")
                .price(1000)
                .imageUrl("abc.png")
                .build();

        Member member = new Member.Builder()
                .email(validEmail)
                .password("abc")
                .build();

        Wish wish = new Wish.Builder()
                .member(member)
                .product(product)
                .count(100)
                .build();

        given(wishRepository.findById(validId)).willReturn(Optional.of(new Wish()));
        given(wishRepository.findById(inValidId)).willReturn(Optional.empty());
        given(wishRepository.findWishByIdAndMemberEmail(validId, validEmail)).willReturn(Optional.of(wish));
        given(wishRepository.findWishByIdAndMemberEmail(validId, inValidEmail)).willReturn(Optional.empty());

        //when
        WishResponseDto wishResponseDto = wishService.deleteWish(validId, validEmail);

        //then
        assertAll(
                () -> assertThat(wishResponseDto.count()).isEqualTo(100),
                () -> assertThat(wishResponseDto.productResponseDto().name()).isEqualTo(product.getName()),
                () -> assertThat(wishResponseDto.productResponseDto().price()).isEqualTo(product.getPrice()),
                () -> assertThat(wishResponseDto.productResponseDto().imageUrl()).isEqualTo(product.getImageUrl()),
                () -> assertThatThrownBy(() -> wishService.deleteWish(inValidId, validEmail))
                        .isInstanceOf(EntityNotFoundException.class)
                        .hasMessage("해당 WISH가 존재하지 않습니다"),
                () -> assertThatThrownBy(() -> wishService.deleteWish(validId, inValidEmail))
                        .isInstanceOf(ForbiddenException.class),
                () -> verify(wishRepository, times(1)).delete(any(Wish.class))
        );
    }

    @Test
    @DisplayName("WISH 전체 조회 테스트")
    void 위시_전체_조회_테스트(){
        //given
        Product product = new Product.Builder()
                .name("테스트 상품")
                .price(1000)
                .imageUrl("abc.png")
                .build();

        Product product2 = new Product.Builder()
                .name("테스트 상품2")
                .price(1000)
                .imageUrl("abc.png")
                .build();

        Member member = new Member.Builder()
                .email("abc@pusan.ac.kr")
                .password("abc")
                .build();

        Wish wish1 = new Wish.Builder()
                .member(member)
                .product(product)
                .count(100)
                .build();

        Wish wish2 = new Wish.Builder()
                .member(member)
                .product(product2)
                .count(100)
                .build();

        List<Wish> wishes = Arrays.asList(wish1, wish2);
        given(wishRepository.findWishByByMemberEmail(member.getEmail())).willReturn(wishes);

        //when
        List<WishResponseDto> wishDtos = wishService.findAllWish(member.getEmail());

        //then
        assertAll(
                () -> assertThat(wishDtos.size()).isEqualTo(2),
                () -> assertThat(wishDtos.get(0).productResponseDto().name()).isEqualTo("테스트 상품")
        );
    }

    @Test
    @DisplayName("WISH 페이징 조회 테스트")
    void 위시_페이징_조회_테스트(){
        //given
        Member member = new Member.Builder()
                .email("abc@pusan.ac.kr")
                .password("abc")
                .build();

        List<Wish> wishes = new ArrayList<>();

        for(int i=0; i<20; i++){
            Product product = new Product.Builder()
                    .name("테스트" + i)
                    .price(i)
                    .imageUrl("abc.png")
                    .build();

            Wish wish = new Wish.Builder()
                    .member(member)
                    .product(product)
                    .count(i)
                    .build();

            wishes.add(wish);
        }

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "count"));

        given(wishRepository.findWishesByMemberEmail(member.getEmail(), pageRequest)).willReturn(new PageImpl<>(wishes.subList(15, 20).reversed(), pageRequest, wishes.size()));

        //when
        List<WishResponseDto> wishDtos = wishService.findWishesPaging(member.getEmail(), pageRequest);

        //then
        assertAll(
                () -> assertThat(wishDtos.size()).isEqualTo(5),
                () -> assertThat(wishDtos.get(0).count()).isEqualTo(19)
        );
    }

}