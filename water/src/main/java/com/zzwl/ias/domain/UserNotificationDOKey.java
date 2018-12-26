package com.zzwl.ias.domain;

public class UserNotificationDOKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_notification.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_notification.article_id
     *
     * @mbg.generated
     */
    private Integer articleId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_notification.user_id
     *
     * @return the value of user_notification.user_id
     *
     * @mbg.generated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_notification.user_id
     *
     * @param userId the value for user_notification.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_notification.article_id
     *
     * @return the value of user_notification.article_id
     *
     * @mbg.generated
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_notification.article_id
     *
     * @param articleId the value for user_notification.article_id
     *
     * @mbg.generated
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
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
        UserNotificationDOKey other = (UserNotificationDOKey) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getArticleId() == null ? other.getArticleId() == null : this.getArticleId().equals(other.getArticleId()));
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
        return result;
    }
}