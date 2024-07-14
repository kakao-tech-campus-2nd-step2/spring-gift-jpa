package gift.global.utility;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

// 정렬 기준을 상수로 반환하는 유틸리티 클래스
// 아직은 가격에 대한 정렬밖에 없어서 boolean으로 짜려고 했으나, 후에 "주문량 많은순" 등으로 확장할 수 있어서 유틸리티 클래스로 만들었습니다.
public class SortingStateUtility {

    public static final int DEFAULT = 0;
    public static final int PRICE_ASC = 1;
    public static final int PRICE_DESC = 2;

    // state가 [1,3] 범위에 들지 않으면 예외를 반환. 유틸리티 클래스에서 최소 최대를 관리하면 한번에 해결이 될 것이라 생각했습니다.
    public static final int STATE_NUM_MIN = 0;
    public static final int STATE_NUM_MAX = 2;

    // 상태에 맞게 Sort 객체를 반환받을 수 있습니다.
    public static Sort getSort(int state) {
        if (state == PRICE_ASC) {
            return Sort.by(Direction.ASC, "price");
        }
        if (state == PRICE_DESC) {
            return Sort.by(Direction.DESC, "price");
        }

        return Sort.unsorted();
    }
}
