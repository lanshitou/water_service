package com.zzwl.ias.dto.device;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.dto.RequestDTO;

import java.util.Date;


/**
 * Description:
 * User: HuXin
 * Date: 2018-09-25
 * Time: 9:16
 */
public class ReqSensorHistoryDataDTO extends RequestDTO{
    private Integer iasId;
    private Integer iasDevId;
    private Long    devId;
    private Integer dataType;
    private Integer collectionType;
    private Date    startDate;
    private Date    endDate;

    @Override
    public void check() {
        AssertEx.isOK(ErrorCode.OK);
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public Integer getIasDevId() {
        return iasDevId;
    }

    public void setIasDevId(Integer iasDevId) {
        this.iasDevId = iasDevId;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Integer collectionType) {
        this.collectionType = collectionType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
