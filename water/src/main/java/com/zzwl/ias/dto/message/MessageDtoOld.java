package com.zzwl.ias.dto.message;

import java.util.Date;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-14
 * Time: 8:12
 */
public class MessageDtoOld<T> {
    private Long id;
    private Integer type;
    private String title;
    private String content;
    private Boolean read;
    private Date createTime;
    private T extension;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public T getExtension() {
        return extension;
    }

    public void setExtension(T extension) {
        this.extension = extension;
    }
}
