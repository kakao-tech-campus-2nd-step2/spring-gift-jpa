package gift.controller;

import gift.controller.auth.LoginRequest;
import gift.controller.auth.LoginResponse;
import gift.controller.member.MemberResponse;
import gift.controller.member.WishMemberResponse;
import gift.controller.product.ProductRequest;
import gift.controller.product.ProductResponse;
import gift.controller.product.WishProductResponse;
import gift.controller.wish.WishCreateRequest;
import gift.controller.wish.WishResponse;
import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import java.util.UUID;

public class GlobalMapper {

    public static Member toMember(LoginRequest member) {
        return new Member(member.email(), member.password());
    }

    public static MemberResponse toMemberResponse(Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getPassword(),
            member.getGrade());
    }

    public static Product toProduct(ProductRequest productRequest) {
        return new Product(productRequest.name(), productRequest.price(),
            productRequest.imageUrl());
    }

    public static ProductResponse toProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl());
    }

    public static LoginResponse toLoginResponse(Member member) {
        return new LoginResponse(member.getId(), member.getEmail(), member.getGrade());
    }

    public static WishResponse toWishResponse(Wish wish) {
        WishMemberResponse wishMember = new WishMemberResponse(wish.getMember().getEmail());
        Product product = wish.getProduct();
        WishProductResponse wishProduct = new WishProductResponse(product.getName(), product.getPrice(), product.getImageUrl());
        return new WishResponse(wishMember, wishProduct, wish.getCount());
    }
}
