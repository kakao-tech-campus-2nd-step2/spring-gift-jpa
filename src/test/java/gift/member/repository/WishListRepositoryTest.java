//package gift.member.repository;
//
//import gift.member.model.Member;
//import gift.wishlist.model.WishList;
//import gift.wishlist.repository.WishListRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class WishListRepositoryTest {
//
//    @Autowired
//    private WishListRepository wishListRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    public void testAddAndFindWishListByMemberId() {
//        // 새로운 멤버를 생성하고 저장함
//        Member member = new Member("test@example.com", "password");
//        memberRepository.save(member);
//
//        // 위시 리스트에 상품을 추가함
//        WishList wishList = new WishList(member, "Test Product");
//        wishListRepository.save(wishList);
//
//        // 멤버의 ID를 사용하여 위시 리스트를 조회함
//        List<WishList> foundWishLists = wishListRepository.findByMemberId(member.id());
//
//        // 위시리스트 값 검증
//        assertThat(foundWishLists).hasSize(1);
//        assertThat(foundWishLists.get(0).getProduct()).isEqualTo("Test Product");
//        assertThat(foundWishLists.get(0).getMember().id()).isEqualTo(member.id());
//    }
//
//    @Test
//    public void testRemoveWishList() {
//        // 새로운 멤버를 생성하고 저장함
//        Member member = new Member("test@example.com", "password");
//        memberRepository.save(member);
//
//        // 위시 리스트에 상품을 추가함
//        WishList wishList = new WishList(member, "Test Product");
//        wishListRepository.save(wishList);
//
//        // 추가된 위시 리스트 항목을 조회하고 삭제까지
//        List<WishList> foundWishLists = wishListRepository.findByMemberId(member.id());
//        assertThat(foundWishLists).hasSize(1);
//
//        wishListRepository.delete(foundWishLists.get(0));
//
//        // 삭제되었는지 확인
//        foundWishLists = wishListRepository.findByMemberId(member.id());
//        assertThat(foundWishLists).isEmpty();
//    }
//}