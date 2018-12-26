package com.zzwl.ias.domain;

import java.util.Date;

public class FarmlandRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmland.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmland.iasystem_id
     *
     * @mbg.generated
     */
    private Integer iasystemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmland.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmland.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmland.is_delete
     *
     * @mbg.generated
     */
    private Short isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmland.delete_time
     *
     * @mbg.generated
     */
    private Date deleteTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column farmland.sort_order
     *
     * @mbg.generated
     */
    private Integer sortOrder;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmland.id
     *
     * @return the value of farmland.id
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmland.id
     *
     * @param id the value for farmland.id
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmland.iasystem_id
     *
     * @return the value of farmland.iasystem_id
     * @mbg.generated
     */
    public Integer getIasystemId() {
        return iasystemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmland.iasystem_id
     *
     * @param iasystemId the value for farmland.iasystem_id
     * @mbg.generated
     */
    public void setIasystemId(Integer iasystemId) {
        this.iasystemId = iasystemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmland.name
     *
     * @return the value of farmland.name
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmland.name
     *
     * @param name the value for farmland.name
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmland.create_time
     *
     * @return the value of farmland.create_time
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmland.create_time
     *
     * @param createTime the value for farmland.create_time
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmland.is_delete
     *
     * @return the value of farmland.is_delete
     * @mbg.generated
     */
    public Short getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmland.is_delete
     *
     * @param isDelete the value for farmland.is_delete
     * @mbg.generated
     */
    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmland.delete_time
     *
     * @return the value of farmland.delete_time
     * @mbg.generated
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmland.delete_time
     *
     * @param deleteTime the value for farmland.delete_time
     * @mbg.generated
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column farmland.sort_order
     *
     * @return the value of farmland.sort_order
     * @mbg.generated
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column farmland.sort_order
     *
     * @param sortOrder the value for farmland.sort_order
     * @mbg.generated
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmland
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
        FarmlandRecord other = (FarmlandRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getIasystemId() == null ? other.getIasystemId() == null : this.getIasystemId().equals(other.getIasystemId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
                && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()))
                && (this.getSortOrder() == null ? other.getSortOrder() == null : this.getSortOrder().equals(other.getSortOrder()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table farmland
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIasystemId() == null) ? 0 : getIasystemId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
        result = prime * result + ((getSortOrder() == null) ? 0 : getSortOrder().hashCode());
        return result;
    }
}