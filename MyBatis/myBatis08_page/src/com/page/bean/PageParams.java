package com.page.bean;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @version : 1.0
 * @auther : Firewine
 * @email : 1451661318@qq.com
 * @ProgramName: <br>
 * @Create : 2018-12-16-20:18
 * @Description : <br/>
 */
public class PageParams {
    //当前页码

    private Integer page;
    //每页条数

    private Integer pageSize;

    //是否启用插件

    private Boolean useFlag;

    //是否检测当前页码的有效性

    private Boolean checkFlag;

    //当前sql返回总数，插件回填

    private Integer total;
    //SQL以当前分页的总页数，插件回填

    private Integer totalPage;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Boolean useFlag) {
        this.useFlag = useFlag;
    }

    public Boolean getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}

