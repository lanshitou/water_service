package com.zzwl.ias.domain;

public class InfoSubjectCatDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column info_subject_cat.id
     *
     * @mbg.generated
     */
    private Short id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column info_subject_cat.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column info_subject_cat.subjectId
     *
     * @mbg.generated
     */
    private Short subjectId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column info_subject_cat.id
     *
     * @return the value of info_subject_cat.id
     *
     * @mbg.generated
     */
    public Short getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column info_subject_cat.id
     *
     * @param id the value for info_subject_cat.id
     *
     * @mbg.generated
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column info_subject_cat.name
     *
     * @return the value of info_subject_cat.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column info_subject_cat.name
     *
     * @param name the value for info_subject_cat.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column info_subject_cat.subjectId
     *
     * @return the value of info_subject_cat.subjectId
     *
     * @mbg.generated
     */
    public Short getSubjectId() {
        return subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column info_subject_cat.subjectId
     *
     * @param subjectId the value for info_subject_cat.subjectId
     *
     * @mbg.generated
     */
    public void setSubjectId(Short subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_subject_cat
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
        InfoSubjectCatDO other = (InfoSubjectCatDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_subject_cat
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        return result;
    }
}