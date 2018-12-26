package com.zzwl.ias.domain;

public class InfoArticleReportDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column info_article_report.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column info_article_report.userId
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column info_article_report.articleId
     *
     * @mbg.generated
     */
    private Integer articleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column info_article_report.reason
     *
     * @mbg.generated
     */
    private String reason;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column info_article_report.id
     *
     * @return the value of info_article_report.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column info_article_report.id
     *
     * @param id the value for info_article_report.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column info_article_report.userId
     *
     * @return the value of info_article_report.userId
     *
     * @mbg.generated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column info_article_report.userId
     *
     * @param userId the value for info_article_report.userId
     *
     * @mbg.generated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column info_article_report.articleId
     *
     * @return the value of info_article_report.articleId
     *
     * @mbg.generated
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column info_article_report.articleId
     *
     * @param articleId the value for info_article_report.articleId
     *
     * @mbg.generated
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column info_article_report.reason
     *
     * @return the value of info_article_report.reason
     *
     * @mbg.generated
     */
    public String getReason() {
        return reason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column info_article_report.reason
     *
     * @param reason the value for info_article_report.reason
     *
     * @mbg.generated
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_article_report
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
        InfoArticleReportDO other = (InfoArticleReportDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getArticleId() == null ? other.getArticleId() == null : this.getArticleId().equals(other.getArticleId()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_article_report
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getArticleId() == null) ? 0 : getArticleId().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        return result;
    }
}