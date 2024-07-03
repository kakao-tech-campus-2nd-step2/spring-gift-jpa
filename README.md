# spring-gift-wishlist

## 1단계
### 클라이언트 입력 값에 대한 검증 및 예외 처리 구현
#### Model(Product)
- 스프링에서 제공하는 Validation 활용
- 공통 조건
  - null 입력 불가
  - 일치하지 않는 타입 입력 불가
- 상품명
    1. 공백을 포함하여 최대 15자까지 입력할 수 있다.
    2. 특수 문자
       - 가능: ( ), [ ], +, -, &, /, _
       - 그 외 특수 문자 사용 불가
    3. "카카오"가 포함된 문구가 입력되면, "담당 MD와 협의한 경우에만 사용할 수 있다"를 출력한다.
- 상품 가격(스스로 판단)
  1. 최소 100원~최대 1,000,000원까지 허용한다.
- 상품 url(스스로 판단)
  1. “http://” 혹은 “https://”로 시작하는 url 양식을 가져야 한다.

#### Controller
- REST API: 스프링 validation 활용하여 검증, ExceptionHandler를 통해 예외 처리
- 관리자 화면 렌더링: 스프링 validation 활용하여 검증, 오류 메시지 출력

#### Exception
- ProductException: 검증 오류 메시지를 포함한 커스텀 예외
- ProductExceptionHandler: 검증 오류 메시지를 포함하여 응답 메시지 반환

#### Validation
- 타입 에러: 스프링에서 기본으로 제공하는 typeMismatch를 메시지 커스텀
- bindingResult: 타입 에러 외 유효하지 않은 input들을 담아 Form html 전송

#### Html
- BindingResult.hasErrors() 시 에러를 출력하도록 수정
  - th:errors/errorclass를 활용하여 에러 출력