package com.zzwl.ias.dto.message;

import java.util.Date;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-23
 * Time: 10:48
 */
public class MessageDTO {
    private Integer id;
    private Integer type;
    private String title;
    private String summary;
    private Date createTime;
    private Boolean read;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
