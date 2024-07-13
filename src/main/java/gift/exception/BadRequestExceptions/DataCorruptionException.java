package gift.exception.BadRequestExceptions;

public class DataCorruptionException extends BadRequestException {

    private DataCorruptionException() {
        super();
    }

    public DataCorruptionException(String message) {
        super(message);
    }
}
