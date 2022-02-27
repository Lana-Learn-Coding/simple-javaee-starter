package io.lana.ejb.lib.pageable;

import io.lana.ejb.lib.utils.StringUtils;

import javax.ws.rs.QueryParam;

public class Pageable {
    public static final int DEFAULT_SIZE = 12;

    private Integer page = 1;
    private Integer size = DEFAULT_SIZE;
    private String sort = "";

    @QueryParam("sort")
    public void setSort(String sort) {
        if (StringUtils.startsWith(sort, "it.") || StringUtils.isBlank(sort)) {
            this.sort = sort;
            return;
        }
        this.sort = "it." + sort;
    }

    @QueryParam("page")
    public void setPage(Integer page) {
        if (page == null || page < 1) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    @QueryParam("size")
    public void setSize(Integer size) {
        if (size == null || size < 1) {
            this.size = DEFAULT_SIZE;
            return;
        }
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public String getSort() {
        return sort;
    }

    // change sort class name in custom query or join query
    public Pageable forRoot(String root) {
        if (!StringUtils.isBlank(sort)) {
            sort = root + "." + sort.substring(3);
        }
        return this;
    }
}
