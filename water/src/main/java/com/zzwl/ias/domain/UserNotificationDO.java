package com.zzwl.ias.domain;

import java.util.Date;

public class UserNotificationDO extends UserNotificationDOKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_notification.verify
     *
     * @mbg.generated
     */
    private Boolean verify;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_notification.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_notification.summary
     *
     * @mbg.generated
     */
    private String summary;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_notification.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_notification.expiration_time
     *
     * @mbg.generated
     */
    private Date expirationTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_notification.verify
     *
     * @return the value of user_notification.verify
     *
     * @mbg.generated
     */
    public Boolean getVerify() {
        return verify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_notification.verify
     *
     * @param verify the value for user_notification.verify
     *
     * @mbg.generated
     */
    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_notification.title
     *
     * @return the value of user_notification.title
     *
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_notification.title
     *
     * @param title the value for user_notification.title
     *
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_notification.summary
     *
     * @return the value of user_notification.summary
     *
     * @mbg.generated
     */
    public String getSummary() {
        return summary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_notification.summary
     *
     * @param summary the value for user_notification.summary
     *
     * @mbg.generated
     */
    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_notification.create_time
     *
     * @return the value of user_notification.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_notification.create_time
     *
     * @param createTime the value for user_notification.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_notification.expiration_time
     *
     * @return the value of user_notification.expiration_time
     *
     * @mbg.generated
     */
    public Date getExpirationTime() {
        return expirationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_notification.expiration_time
     *
     * @param expirationTime the value for user_notification.expiration_time
     *
     * @mbg.generated
     */
    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_notification
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
        UserNotificationDO other = (UserNotificationDO) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getArticleId() == null ? other.getArticleId() == null : this.getArticleId().equals(other.getArticleId()))
            && (this.getVerify() == null ? other.getVerify() == null : this.getVerify().equals(other.getVerify()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getSummary() == null ? other.getSummary() == null : this.getSummary().equals(other.getSummary()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getExpirationTime() == null ? other.getExpirationTime() == null : this.getExpirationTime().equals(other.getExpirationTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_notification
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getArticleId() == null) ? 0 : getArticleId().hashCode());
        result = prime * result + ((getVerify() == null) ? 0 : getVerify().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getSummary() == null) ? 0 : getSummary().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getExpirationTime() == null) ? 0 : getExpirationTime().hashCode());
        return result;
    }
}