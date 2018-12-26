package com.zzwl.ias.dto.warning;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.WarningDO;
import com.zzwl.ias.dto.common.IasObjectAddrDTO;
import com.zzwl.ias.iasystem.IasObject;
import com.zzwl.ias.iasystem.IasObjectAddr;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;

import java.util.Date;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-15
 * Time: 10:47
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WarningDTO {
    private Long id;
    private Integer type;
    private Integer subType;
    private Integer level;
    private Boolean cleared;
    private Date produceTime;
    private Date clearTime;
    private Integer clearReason;
    private IasObjectAddrDTO addr;
    private Object ext;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getCleared() {
        return cleared;
    }

    public void setCleared(Boolean cleared) {
        this.cleared = cleared;
    }

    public Date getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(Date produceTime) {
        this.produceTime = produceTime;
    }

    public Date getClearTime() {
        return clearTime;
    }

    public void setClearTime(Date clearTime) {
        this.clearTime = clearTime;
    }

    public Integer getClearReason() {
        return clearReason;
    }

    public void setClearReason(Integer clearReason) {
        this.clearReason = clearReason;
    }

    public IasObjectAddrDTO getAddr() {
        return addr;
    }

    public void setAddr(IasObjectAddrDTO addr) {
        this.addr = addr;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }
}
