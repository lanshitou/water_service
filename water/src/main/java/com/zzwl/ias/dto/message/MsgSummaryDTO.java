package com.zzwl.ias.dto.message;

import java.util.Date;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-20
 * Time: 14:46
 */
public class MsgSummaryDTO {
    private Long id;
    private Integer type;
    private String title;
    private String summary;
    private Date createDate;
    private Boolean read;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
