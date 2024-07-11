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
    public static ResponseEntity<BasicResponse> of(final HttpStatusCode statusCode) {
        return ResponseEntity
            .status(statusCode)
            .body(new BasicResponse(statusCode));
    }

    public static ResponseEntity<BasicResponse> of (final HttpHeaders headers, final HttpStatusCode statusCode) {
        return ResponseEntity
            .status(statusCode)
            .headers(headers)
            .body(new BasicResponse(statusCode));
    }

    // status 코드에 대한 기본적인 RESTful 응답을 생성
    public static <DTO> ResponseEntity<DTO> of(final DTO dto, final HttpStatusCode statusCode) {
        return ResponseEntity
            .status(statusCode)
            .body(dto);
    }

    public static <DTO> ResponseEntity<DTO> of(final DTO dto, final HttpHeaders headers, final HttpStatusCode statusCode) {
        return ResponseEntity
            .status(statusCode)
            .headers(headers)
            .body(dto);
    }

    // HTTP code 200에 대한 기본 응답 생성
    public static ResponseEntity<BasicResponse> ok() {
        return SuccessResponse.of(HttpStatus.OK);
    }

    // HTTP code 200에 대한 응답 생성 (with data)
    public static <DTO> ResponseEntity<DTO> ok(final DTO dto) {
        return SuccessResponse.of(dto, HttpStatus.OK);
    }

    // HTTP code 201에 대한 응답 생성 (with header/Location, data)
    public static <DTO> ResponseEntity<DTO> created(final DTO dto,
                                                    final String url,
                                                    final Object... uriVariableValues) {
        // Header에 생성된 리소스의 Location 정보 추가
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder
            .fromPath(url)
            .buildAndExpand(uriVariableValues)
            .toUri());

        // 응답 생성
        return SuccessResponse.of(dto, headers, HttpStatus.CREATED);
    }
}
