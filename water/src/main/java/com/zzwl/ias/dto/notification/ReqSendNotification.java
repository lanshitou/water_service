package com.zzwl.ias.dto.notification;

import java.util.Date;
import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-10-15
 * Time: 11:09
 */
public class ReqSendNotification {
    private LinkedList<Integer> users;
    private String title;
    private String summary;
    private Integer articleId;
    private Date expirationTime;

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public LinkedList<Integer> getUsers() {
        return users;
    }

    public void setUsers(LinkedList<Integer> users) {
        this.users = users;
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

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
}
