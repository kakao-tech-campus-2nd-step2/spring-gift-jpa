package gift.dto.common.apiResponse;


import org.springframework.http.HttpStatus;

public class ApiResponseGenerator {
    public static ApiResponse<ApiResponseBody.SuccessBody<Void>> success(HttpStatus status, String message){
        return new ApiResponse<>(new ApiResponseBody.SuccessBody<>(String.valueOf(status.value()), message, null), status);
    }

    public static <D>ApiResponse<ApiResponseBody.SuccessBody<D>> success(HttpStatus status, String message, D data){
        return new ApiResponse<>(new ApiResponseBody.SuccessBody<>(String.valueOf(status.value()), message, data), status);
    }

    public static ApiResponse<ApiResponseBody.FailureBody> fail(HttpStatus status, String message){
        return new ApiResponse<>(new ApiResponseBody.FailureBody(String.valueOf(status), message, null), status);
    }
    //Todo: 에러 코드 만들기
    public static ApiResponse<ApiResponseBody.FailureBody> fail(HttpStatus status, String message, String code){
        return new ApiResponse<>(new ApiResponseBody.FailureBody(String.valueOf(status), message, code), status);
    }
}
