package gift.wishlist.model;

import gift.member.model.Member;
import gift.product.model.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToMany
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    // JPA에서 필요로 하는 기본 생성자
    public WishList() {
    }

    public WishList(Member member, Product product) {
        // products = product; // 이런 식으로 하나의 Product 객체를 List<Product> 타입에 할당하려고 할 때 오류 발생
        products = new ArrayList<>(); // List를 초기화하고 요소를 추가해야 함
        products.add(product); // Product 객체 추가
    }

    // hashCode 및 equals 메서드 (optional, 테스트를 위한 비교에 사용)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishList wishList = (WishList) o;
        return id != null && id.equals(wishList.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // 위시리스트의 ID 반환
    public Long getId() {
        return id;
    }

    // 회원 반환 메서드
    public Member getMember() {
        return member;
    }

    // 회원 설정 메서드
    public void setMember(Member member) {
        this.member = member;
    }

    // 불변의 제품 리스트 반환 메서드
    public List<Product> getProducts() {
        return products;
    }

    // Products 리스트를 조작하는 메서드
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }}