package com.zhk.domain.vo;

import java.util.List;

public class PageBean<T> {

    private Integer page;

    private Integer size;

    private Long total;

    private Integer totalPage;

    private List<T> list;

    private Integer pre;

    private Integer next;

    private Integer beg;

    private Integer end;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPre() {
        return pre;
    }

    public void setPre(Integer pre) {
        this.pre = pre;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public Integer getBeg() {
        return beg;
    }

    public void setBeg(Integer beg) {
        this.beg = beg;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public PageBean(Integer page, Integer size, Long total, List<T> list) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.list = list;

        this.pre = page-1;
        this.next = page+1;

        this.totalPage =(int) (total%size==0?total/size:(total/size)+1);

        calPageRange();

    }

    private void calPageRange() {
        if(totalPage<=5){
            beg=1;
            end=totalPage;
        }else {
            if(page<=3){
                beg=1;
                end=5;
            }else if(totalPage-page<=2){
                beg=totalPage-4;
                end=totalPage;
            }else {
                beg=page-2;
                end=page+2;
            }

        }
    }
}
