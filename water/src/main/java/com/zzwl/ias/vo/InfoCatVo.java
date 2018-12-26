package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Lvpin on 2018/11/28.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InfoCatVo {
    private Integer id;
    private Integer pid;
    private String name;
    private List<InfoCatVo> children;

    public List<InfoCatVo> getChildren() {
        return children;
    }

    public void setChildren(List<InfoCatVo> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
