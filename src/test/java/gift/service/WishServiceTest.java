package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.MemberRequest;
import gift.dto.WishRequest;
import gift.entity.MemberEntity;
import gift.entity.ProductEntity;
import gift.entity.WishEntity;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WishServiceTest {
    @InjectMocks
    WishService wishService;
    @Mock
    WishRepository wishRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("getWishesByMember 테스트")
    void getWishesByMember() {
        // given
        Member savedMember = new Member(1L, "email@google.co.kr", "password");

        MemberEntity member = new MemberEntity(savedMember.getEmail(), savedMember.getPassword());

        ProductEntity product1 = new ProductEntity("product1", 1000, "product1.jpg");
        ProductEntity product2 = new ProductEntity("product2", 2000, "product2.jpg");

        WishEntity wish1 = new WishEntity(member, product1);
        WishEntity wish2 = new WishEntity(member, product2);
        List<WishEntity> wishList = Arrays.asList(wish1, wish2);

        Wish expected1 = new Wish(wish1.getMemberEntity().getId(), wish1.getProductEntity().getId());
        Wish expected2 = new Wish(wish2.getMemberEntity().getId(), wish2.getProductEntity().getId());
        List<Wish> expected = Arrays.asList(expected1, expected2);

        doReturn(member).when(memberRepository).findById(savedMember.getId());
        doReturn(wishList).when(wishRepository).findAllByMemberEntity(member);

        // when
        List<Wish> actual = wishService.getWishesByMember(savedMember);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(2);
    }
    @Test
    @DisplayName("위시 리스트 추가 테스트")
    void addWish() {
        // given
        WishRequest wishRequest = new WishRequest(1L, 1L);

        Member savedMember = new Member(1L, "email@google.com", "password");
        Product savedProduct = new Product(1L, "test", 1000, "test.jpg");

        MemberEntity member = new MemberEntity(savedMember.getEmail(), savedMember.getPassword());
        ProductEntity product = new ProductEntity(savedProduct.getName(), savedProduct.getPrice(), savedProduct.getImageUrl());

        WishEntity wishEntity = new WishEntity(member, product);
        Wish expected = new Wish(1L, savedMember.getId(), savedProduct.getId());

        doReturn(product).when(productRepository).findById(wishRequest.getProductId());
        doReturn(member).when(memberRepository).findById(wishRequest.getMemberId());
        doReturn(wishEntity).when(wishRepository).save(any(WishEntity.class));

        // when
        Wish actual = wishService.addWish(wishRequest);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("위시 리시트 삭제 테스트")
    void deleteWish() {
        Long id = 1L;
        Member savedMember = new Member(1L, "email@google.co.kr", "password");
        Product savedProduct = new Product(1L, "test", 1000, "test.jpg");

        MemberEntity member = new MemberEntity(savedMember.getEmail(), savedMember.getPassword());
        ProductEntity product = new ProductEntity(savedProduct.getName(), savedProduct.getPrice(), savedProduct.getImageUrl());

        WishEntity wishEntity = new WishEntity(member, product);

        doReturn(Optional.of(wishEntity)).when(wishRepository).findById(id);
        wishService.deleteWish(id, savedMember);
        verify(wishRepository, times(1)).delete(wishEntity);
    }
}