package com.silita.notice.utils;

/**
 * Created by zhushuai on 2019/9/6.
 */
public class Page {

    /**
     * 当前页数
     */
    private Integer pageNo;
    /**
     * 页面条数数
     */
    private Integer pageSize = 20;
    /**
     * 总条数
     */
    private Integer total;
    /**
     * 总页数
     */
    private Integer pages;

    public Integer getPageNo() {
        return (pageNo - 1) * pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPages() {
        if (total % pageSize == 0) {
            return total / pageSize;
        }
        return (total / pageSize) + 1;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Page(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo == null ? 1 : pageNo;
        this.pageSize = pageSize;
    }
}
