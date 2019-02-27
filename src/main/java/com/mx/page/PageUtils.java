package com.mx.page;

import java.io.Serializable;
import java.util.List;

/**
 * @author 小米线儿
 * @time 2019/2/24 0024
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class PageUtils implements Serializable {
    private static final long serialVersionUID = -9094694528454668313L;
    private int total;
    private List<?> rows;

    public PageUtils(List<?> list, int total) {
        this.rows = list;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
