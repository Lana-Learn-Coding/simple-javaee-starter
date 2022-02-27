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

    public static <T> Page<T> empty(Pageable pageable) {
        pageable.setPage(1);
        return new Page<>(new ArrayList<>(), PageMeta.from(pageable, 0));
    }

    public static <T> Page<T> from(List<T> data, Pageable pageable, long count) {
        return new Page<>(data, PageMeta.from(pageable, count));
    }
}
