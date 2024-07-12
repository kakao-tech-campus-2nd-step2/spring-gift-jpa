package gift.dto;

public class PagingRequest {
    int page;
    int size;

    public PagingRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
