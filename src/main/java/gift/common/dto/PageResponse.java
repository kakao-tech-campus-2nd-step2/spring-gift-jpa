package gift.common.dto;

import java.util.List;

public class PageResponse<E> {

    private List<E> responses;
    private int page;
    private int size;
    private boolean prev, next;
    private int totalCount, prevPage, nextPage;

    public PageResponse(List<E> responses, int page, int size, int totalCount) {
        this.responses = responses;
        this.page = page;
        this.size = size;
        this.totalCount = totalCount;

        int end = (int)(Math.ceil(page / 10.0)) * 10;
        int start = end - 9;
        int last = (int) (Math.ceil(totalCount / (double) size));
        end = Math.min(end, last);

        this.prev = start > 1;
        this.next = totalCount > end * size;

        this.prevPage = prev ? start - 1 : 0;
        this.nextPage = next ? end + 1 : 0;
    }

    public List<E> getResponses() {
        return responses;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public boolean isPrev() {
        return prev;
    }

    public boolean isNext() {
        return next;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }
}
