package com.fh.shop.admin.param;

import java.io.Serializable;

public class Page implements Serializable {

    private Long draw;

    private Long start;

    private Long  length;

    public Long getDraw() {
        return draw;
    }

    public void setDraw(Long draw) {
        this.draw = draw;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }
}
