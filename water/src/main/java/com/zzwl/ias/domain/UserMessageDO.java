package com.zzwl.ias.domain;

import java.util.Date;

public class UserMessageDO extends UserMessageDOKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_message.verified
     *
     * @mbg.generated
     */
    private Boolean verified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_message.read_time
     *
     * @mbg.generated
     */
    private Date readTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_message.verified
     *
     * @return the value of user_message.verified
     *
     * @mbg.generated
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_message.verified
     *
     * @param verified the value for user_message.verified
     *
     * @mbg.generated
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_message.read_time
     *
     * @return the value of user_message.read_time
     *
     * @mbg.generated
     */
    public Date getReadTime() {
        return readTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_message.read_time
     *
     * @param readTime the value for user_message.read_time
     *
     * @mbg.generated
     */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
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
        UserMessageDO other = (UserMessageDO) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getMsgId() == null ? other.getMsgId() == null : this.getMsgId().equals(other.getMsgId()))
            && (this.getVerified() == null ? other.getVerified() == null : this.getVerified().equals(other.getVerified()))
            && (this.getReadTime() == null ? other.getReadTime() == null : this.getReadTime().equals(other.getReadTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_message
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getVerified() == null) ? 0 : getVerified().hashCode());
        result = prime * result + ((getReadTime() == null) ? 0 : getReadTime().hashCode());
        return result;
    }
}