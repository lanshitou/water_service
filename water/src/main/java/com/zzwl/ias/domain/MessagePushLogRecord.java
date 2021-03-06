package com.zzwl.ias.domain;

import java.util.Date;

public class MessagePushLogRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_push_log.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_push_log.msg_id
     *
     * @mbg.generated
     */
    private Long msgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_push_log.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_push_log.content
     *
     * @mbg.generated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_push_log.extra
     *
     * @mbg.generated
     */
    private String extra;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_push_log.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message_push_log.userIds
     *
     * @mbg.generated
     */
    private String userids;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_push_log.id
     *
     * @return the value of message_push_log.id
     * @mbg.generated
     */

    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_push_log.id
     *
     * @param id the value for message_push_log.id
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_push_log.msg_id
     *
     * @return the value of message_push_log.msg_id
     * @mbg.generated
     */
    public Long getMsgId() {
        return msgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_push_log.msg_id
     *
     * @param msgId the value for message_push_log.msg_id
     * @mbg.generated
     */
    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_push_log.title
     *
     * @return the value of message_push_log.title
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_push_log.title
     *
     * @param title the value for message_push_log.title
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_push_log.content
     *
     * @return the value of message_push_log.content
     * @mbg.generated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_push_log.content
     *
     * @param content the value for message_push_log.content
     * @mbg.generated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_push_log.extra
     *
     * @return the value of message_push_log.extra
     * @mbg.generated
     */
    public String getExtra() {
        return extra;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_push_log.extra
     *
     * @param extra the value for message_push_log.extra
     * @mbg.generated
     */
    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_push_log.status
     *
     * @return the value of message_push_log.status
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_push_log.status
     *
     * @param status the value for message_push_log.status
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message_push_log.userIds
     *
     * @return the value of message_push_log.userIds
     * @mbg.generated
     */
    public String getUserids() {
        return userids;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message_push_log.userIds
     *
     * @param userids the value for message_push_log.userIds
     * @mbg.generated
     */
    public void setUserids(String userids) {
        this.userids = userids == null ? null : userids.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_push_log
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MessagePushLogRecord other = (MessagePushLogRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getMsgId() == null ? other.getMsgId() == null : this.getMsgId().equals(other.getMsgId()))
                && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
                && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
                && (this.getExtra() == null ? other.getExtra() == null : this.getExtra().equals(other.getExtra()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getUserids() == null ? other.getUserids() == null : this.getUserids().equals(other.getUserids()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table message_push_log
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getUserids() == null) ? 0 : getUserids().hashCode());
        return result;
    }
}