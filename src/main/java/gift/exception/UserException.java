package gift.exception;

public class UserException {

    public static class Forbidden extends RuntimeException {

        private final UserErrorCode errorCode;
        private final String detailMessage;

        public Forbidden(UserErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
            this.detailMessage = errorCode.getMessage();
        }

        public Forbidden(UserErrorCode errorCode, String detailMessage) {
            super(detailMessage);
            this.errorCode = errorCode;
            this.detailMessage = detailMessage;
        }

        public UserErrorCode getErrorCode() {
            return this.errorCode;
        }

        public String getDetailMessage() {
            return this.detailMessage;
        }
    }

    public static class BadRequest extends RuntimeException {

        private final UserErrorCode errorCode;
        private final String detailMessage;

        public BadRequest(UserErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
            this.detailMessage = errorCode.getMessage();
        }

        public BadRequest(UserErrorCode errorCode, String detailMessage) {
            super(detailMessage);
            this.errorCode = errorCode;
            this.detailMessage = detailMessage;
        }

        public UserErrorCode getErrorCode() {
            return this.errorCode;
        }

        public String getDetailMessage() {
            return this.detailMessage;
        }
    }

    public static class Conflict extends RuntimeException {

        private final UserErrorCode errorCode;
        private final String detailMessage;

        public Conflict(UserErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
            this.detailMessage = errorCode.getMessage();
        }

        public Conflict(UserErrorCode errorCode, String detailMessage) {
            super(detailMessage);
            this.errorCode = errorCode;
            this.detailMessage = detailMessage;
        }

        public UserErrorCode getErrorCode() {
            return this.errorCode;
        }

        public String getDetailMessage() {
            return this.detailMessage;
        }
    }

    public static class NotFound extends RuntimeException {

        private final UserErrorCode errorCode;
        private final String detailMessage;

        public NotFound(UserErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
            this.detailMessage = errorCode.getMessage();
        }

        public NotFound(UserErrorCode errorCode, String detailMessage) {
            super(detailMessage);
            this.errorCode = errorCode;
            this.detailMessage = detailMessage;
        }

        public UserErrorCode getErrorCode() {
            return this.errorCode;
        }

        public String getDetailMessage() {
            return this.detailMessage;
        }
    }

    public static class BadToken extends RuntimeException {

        private final UserErrorCode errorCode;
        private final String detailMessage;

        public BadToken(UserErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
            this.detailMessage = errorCode.getMessage();
        }

        public BadToken(UserErrorCode errorCode, String detailMessage) {
            super(detailMessage);
            this.errorCode = errorCode;
            this.detailMessage = detailMessage;
        }

        public UserErrorCode getErrorCode() {
            return this.errorCode;
        }

        public String getDetailMessage() {
            return this.detailMessage;
        }
    }
}
