package gift.Model;

import jakarta.persistence.*;

@Entity
@Table(name="wishlist")
public class Wishlist{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="member_id")
        private Long memberId;

        @Column(name="product_id")
        private Long productId;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Long getMemberId() {
                return memberId;
        }

        public void setMemberId(Long memberId) {
                this.memberId = memberId;
        }

        public Long getProductId() {
                return productId;
        }

        public void setProductId(Long productId) {
                this.productId = productId;
        }

        public Wishlist(){}

        public Wishlist(Long memberId, Long productId){
                this.memberId = memberId;
                this.productId = productId;
        }
}
