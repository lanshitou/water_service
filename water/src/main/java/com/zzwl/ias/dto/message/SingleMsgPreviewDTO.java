package com.zzwl.ias.dto.message;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-20
 * Time: 16:37
 */
public class SingleMsgPreviewDTO {
    private String title;
    private Integer unreadCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }
}
