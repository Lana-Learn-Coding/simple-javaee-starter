package io.lana.ejb.lib.pageable;

import lombok.Data;

@Data
public class Pageable {
    public static final int DEFAULT_SIZE = 12;

    private Integer page = 1;
    private Integer size = DEFAULT_SIZE;
    private String sort = "";

    public void setPage(Integer page) {
        if (page == null || page < 1) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public void setSize(Integer size) {
        if (size == null || size < 1) {
            this.size = DEFAULT_SIZE;
            return;
        }
        this.size = size;
    }
}
