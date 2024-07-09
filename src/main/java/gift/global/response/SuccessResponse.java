package gift.global.response;

import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 성공 HTTP 응답 생성을 보조하는 유틸리티 클래스
 */
public class SuccessResponse {

    // status 코드에 대한 기본적인 RESTful 응답을 생성
    public static ResponseEntity<Map<String, Object>> of(final HttpStatusCode statusCode) {
        return ResponseEntity
            .status(statusCode)
            .body(ResponseBodyBase.get(statusCode));
    }

    // 기본적인 응답에 사용자 정의 데이터 추가시 사용
    public static <T> ResponseEntity<Map<String, Object>> of(final T data,
                                                             final String dataLabel,
                                                             final HttpStatusCode statusCode) {
        return ResponseEntity
            .status(statusCode)
            .body(ResponseBodyBase.get(
                statusCode,
                ResponseBodyBase.Entry.of(dataLabel, data)));
    }

    // HTTP code 200에 대한 기본 응답 생성
    public static ResponseEntity<Map<String, Object>> ok() {
        return SuccessResponse.of(HttpStatus.OK);
    }

    // HTTP code 200에 대한 응답 생성 (with data)
    public static <T> ResponseEntity<Map<String, Object>> ok(final T data, final String dataLabel) {
        return SuccessResponse.of(data, dataLabel, HttpStatus.OK);
    }

    // HTTP code 201에 대한 응답 생성 (with header/Location, data)
    public static <T> ResponseEntity<Map<String, Object>> created(final T data,
                                                                  final String dataLabel,
                                                                  final String url,
                                                                  final Object... uriVariableValues) {
        // Header에 생성된 리소스의 Location 정보 추가
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder
            .fromPath(url)
            .buildAndExpand(uriVariableValues)
            .toUri());

        // 응답 생성
        return ResponseEntity.status(HttpStatus.CREATED)
            .headers(headers)
            .body(ResponseBodyBase.get(
                HttpStatus.CREATED,
                ResponseBodyBase.Entry.of(dataLabel, data)));
    }
}
