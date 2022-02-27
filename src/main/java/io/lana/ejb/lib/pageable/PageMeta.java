package io.lana.ejb.lib.pageable;

public class PageMeta {
    private final String sort;
    private final int size;
    private final int currentPage;
    private final int totalPages;
    private final long totalElements;

    public PageMeta(String sort, int size, int currentPage, int totalPages, long totalElements) {
        this.sort = sort;
        this.size = size;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public static PageMeta from(Pageable pageable, long count) {
        int totalPages = (int) Math.ceil((double) count / pageable.getSize());
        return new PageMeta(pageable.getSort(), pageable.getSize(), pageable.getPage(), totalPages, count);
    }

    public String getSort() {
        return sort;
    }

    public int getSize() {
        return size;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }
}
