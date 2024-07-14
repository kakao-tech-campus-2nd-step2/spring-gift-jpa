package gift.dto;

import gift.entity.Product;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ProductDto {

    public static boolean checkValidProductName(String name) {
        boolean result = true;
        if (!(checkValidLength(name, 1, 15))) {
            result = false;
            throw new IllegalProductNameLengthException();
        }
        if (!(checkValidSpecialCharacter(name))) {
            result = false;
            throw new IllegalProductNameCharacterException();
        }
        if (!(checkNotContainKeyword(name, "카카오"))) {
            result = false;
            throw new IllegalProductNameKeywordException();
        }
        return result;
    }

    private static boolean checkValidLength(String name, int start, int end) {
        return (name.length() >= 1 && name.length() <= end);
    }

    private static boolean checkValidSpecialCharacter(String name) {
        boolean result = true;

        char[] nameWords = name.toCharArray();
        for (char word : nameWords) {
            if (checkSpecialCharacter(word) && (!checkAllowedSpecialCharacter(word))) {
                result = false;
            }
        }
        return result;
    }

    private static boolean checkAllowedSpecialCharacter(char word) {
        char[] allowedSpecialCharacters = {'(', ')', '[', ']', '+', '-', '&', '/', '_'};
        for (char allowedSpecialCharacter : allowedSpecialCharacters) {
            if (word == allowedSpecialCharacter) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkSpecialCharacter(char word) {
        boolean result = (word < 'a' || word > 'z') &&
                (word < 'A' || word > 'Z') &&
                (word < '0' || word > '9') &&
                (word < '가' || word > '힣') &&
                (word != ' ');
        return result;
    }

    private static boolean checkNotContainKeyword(String name, String keyword) {
        return !(name.contains(keyword));
    }

    @ExceptionHandler({IllegalProductNameLengthException.class, IllegalProductNameCharacterException.class, IllegalProductNameKeywordException.class})
    public static String handleProductNameExceptions(RuntimeException e) {
        return e.getMessage();
    }

    public static class Request {

        private Long id;
        private String name;
        private Long price;
        private String url;

        public Request(String name, Long price, String url) {
            if (checkValidProductName(name)) {
                this.name = name;
                this.price = price;
                this.url = url;
            }
        }

        public Request(Long id, String name, Long price, String url) {
            if (checkValidProductName(name)) {
                this.id = id;
                this.name = name;
                this.price = price;
                this.url = url;
            }
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Long getPrice() {
            return price;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class Response {
        private final String name;
        private final String url;
        private final Long price;
        private Long id;

        public Response(Long id, String name, Long price, String url) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.url = url;
        }

        public Response(String name, Long price, String url) {
            this.name = name;
            this.price = price;
            this.url = url;
        }

        public static Response fromEntity(Product product) {
            return new Response(product.getName(), product.getPrice(), product.getUrl());
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Long getPrice() {
            return price;
        }

        public String getUrl() {
            return url;
        }

    }

    public static class IllegalProductNameLengthException extends IllegalArgumentException {
        public IllegalProductNameLengthException() {
            super("상품 이름은 1 ~ 15 글자로 입력해주세요.");
        }
    }

    public static class IllegalProductNameCharacterException extends IllegalArgumentException {
        public IllegalProductNameCharacterException() {
            super("상품 이름에서 특수문자는 ()[] + - & / _ 만 사용 가능합니다.");
        }
    }

    public static class IllegalProductNameKeywordException extends IllegalArgumentException {
        public IllegalProductNameKeywordException() {
            super("상품이름에 \"카카오\" 키워드를 포함시키고 싶으신 경우, 담당MD와의 협의가 필요합니다. 고객센터로 문의주세요.");
        }
    }
}
