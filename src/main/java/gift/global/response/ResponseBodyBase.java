package gift.global.response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatusCode;

/**
 * ResponseEntity의 body에 담길 기본 객체(Map<String, Object)를 생성하는 유틸리티 클래스
 */
public class ResponseBodyBase {

    // 가장 기본이 되는 Body를 생성
    public static Map<String, Object> get(Integer status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        return body;
    }

    public static Map<String, Object> get(HttpStatusCode statusCode) {
        return get(statusCode.value());
    }

    // 기본이 되는 Body에 새 속성 추가하고 싶을 시 사용
    public static Map<String, Object> get(HttpStatusCode statusCode, Entry... attributes) {
        var body = get(statusCode);
        for (var entry: attributes) {
            body.put(entry.getKey(), entry.getValue());
        }
        return body;
    }

    // Map.Entry 구현체
    public static class Entry implements Map.Entry<String, Object> {

        private final String key;
        private Object value;

        public static Entry of(String key, Object value) {
            return new Entry(key, value);
        }

        public Entry(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            Object prev = this.value;
            this.value = value;
            return prev;
        }
    }
}
