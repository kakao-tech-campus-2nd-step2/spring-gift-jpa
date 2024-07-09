package gift.util.page;

import gift.errorException.BaseResult;

public class SingleResult<T> extends BaseResult {

    private final T resultData;

    public SingleResult(T value) {
        this.resultData = value;
    }

    public T getResultData() {
        return resultData;
    }
}