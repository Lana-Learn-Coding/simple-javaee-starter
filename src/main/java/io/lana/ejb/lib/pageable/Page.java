package io.lana.ejb.lib.pageable;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
    private final List<T> data;
    private final PageMeta meta;

    private Page(List<T> data, PageMeta meta) {
        this.meta = meta;
        this.data = new ArrayList<>(data);
    }

    public List<T> getData() {
        return data;
    }

    public PageMeta getMeta() {
        return meta;
    }

    public static class PageMeta {
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

    public static <T> Page<T> empty(Pageable pageable) {
        pageable.setPage(1);
        return new Page<>(new ArrayList<>(), PageMeta.from(pageable, 0));
    }

    public static <T> Page<T> from(List<T> data, Pageable pageable, long count) {
        return new Page<>(data, PageMeta.from(pageable, count));
    }
}
