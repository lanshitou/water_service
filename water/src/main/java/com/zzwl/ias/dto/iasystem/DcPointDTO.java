package com.zzwl.ias.dto.iasystem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.IasDeviceRecord;
import com.zzwl.ias.iasystem.constant.WarningConstant;
/**
 * Description:
 * User: HuXin
 * Date: 2018-05-25
 * Time: 17:18
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DcPointDTO {
    private Integer id;             //设备的IAS_ID
    private Integer type;           //采集值类型
    private String name;            //采集值名称
    private Integer value;          //采集值
    private Integer status;         //设备状态
    private Integer alarmType;      //告警类型
    private Boolean configWarn;     //用户是否可以配置告警

    public DcPointDTO(IasDeviceRecord iasDeviceRecord) {
        id = iasDeviceRecord.getId();
        name = iasDeviceRecord.getName();
        alarmType = WarningConstant.WARNING_NONE;
        configWarn = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public Boolean getConfigWarn() {
        return configWarn;
    }

    public void setConfigWarn(Boolean configWarn) {
        this.configWarn = configWarn;
    }
}
