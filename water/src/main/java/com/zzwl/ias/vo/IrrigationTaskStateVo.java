package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.IrrigationTaskRecord;
import com.zzwl.ias.domain.MessageDO;
import com.zzwl.ias.domain.User;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrriTaskResult;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTaskState;
import com.zzwl.ias.mapper.UserMapper;

import java.io.IOException;
import java.util.Date;

/**
 * Created by HuXin on 2018/2/28.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IrrigationTaskStateVo {
    private Integer iasId;
    private Integer farmlandId;
    private String farmlandName;
    private Integer irriAreaId;
    private String irriAreaName;
    private Integer expDuration;            //用户设置的浇水时间
    private Integer irriDuration;           //实际浇水时间
    private Integer status;                 //任务状态
    private Date addTime;                   //任务添加时间
    private Date startTime;                 //任务启动时间或者预期启动时间
    private Date finishTime;                //任务结束时间或者预期完成时间
    private UserBasicInfoVo createUser;     //创建该任务的用户
    private UserBasicInfoVo deleteUser;     //停止/删除该任务的用户
    private NormalIrriTaskResult result;    //任务执行的结果

    @JsonIgnore
    private boolean flag;                   //该标记用于任务时间的计算
    @JsonIgnore
    private Date irriStartTime;             //灌溉启动时间，用于计算实际运行时间

    public IrrigationTaskStateVo(){}

    public IrrigationTaskStateVo(IrrigationTaskRecord irrigationTaskRecord) {
        farmlandId = irrigationTaskRecord.getFarmlandId();
        irriAreaId = irrigationTaskRecord.getIrriAreaId();
        expDuration = irrigationTaskRecord.getExpDuration();
        irriDuration = irrigationTaskRecord.getIrriDuration();
        status = irrigationTaskRecord.getStatus();
        addTime = irrigationTaskRecord.getCreateTime();
        startTime = irrigationTaskRecord.getStartTime();
        finishTime = irrigationTaskRecord.getFinishTime();
        createUser = null;
        deleteUser = null;
        if (irrigationTaskRecord.getCreateUser() != null) {
            createUser = new UserBasicInfoVo(irrigationTaskRecord.getCreateUser());
        }
        if (irrigationTaskRecord.getDeleteUser() != null) {
            deleteUser = new UserBasicInfoVo(irrigationTaskRecord.getDeleteUser());
        }
        result = NormalIrriTaskResult.getByValue(irrigationTaskRecord.getResult());
    }

    public static IrrigationTaskStateVo getFromJson(String json){
        try {
            return AppContext.objectMapper
                    .readValue(json, IrrigationTaskStateVo.class);
        } catch (IOException e) {
            return null;
        }
    }

    public void stateTranslateForApi(){
        switch (status){
            case NormalIrrigationTaskState.STATE_WAITING:{
                status = 1;
                break;
            }
            case NormalIrrigationTaskState.STATE_STARTING_VALVES:
            case NormalIrrigationTaskState.STATE_WAITING_PUMP_STARTED: {
                status = 2;
                break;
            }
            case NormalIrrigationTaskState.STATE_IRRIGATING:{
                status = 3;
                break;
            }
            case NormalIrrigationTaskState.STATE_WAITING_FINISH:
            case NormalIrrigationTaskState.STATE_STOPPING_VALVES:{
                status = 4;
                break;
            }
            case NormalIrrigationTaskState.STATE_FINISH:{
                status = 5;
                break;
            }
            default:{
                //do nothing
            }
        }
    }

    public void insertUserInfo(){
        if (!insertUserInfo(createUser)){
            createUser = null;
        }
        if (!insertUserInfo(deleteUser)){
            deleteUser = null;
        }
    }

    private boolean insertUserInfo(UserBasicInfoVo user){
        if (user != null){
            User u = AppContext.getBean(UserMapper.class).selectByPrimaryKey(user.getUid());
            if (u != null) {
                user.setUsername(u.getUsername());
                user.setHeadImage(u.getImage());
            }else{
                return false;
            }
        }
        return true;
    }

    public void setResult(Integer result) {
        this.result = NormalIrriTaskResult.getByValue(result);
    }

    public void setResultDesc(String resultDesc){}

    public String getResultDesc() {
        return result.getMsg();
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public Integer getFarmlandId() {
        return farmlandId;
    }

    public void setFarmlandId(Integer farmlandId) {
        this.farmlandId = farmlandId;
    }

    public Integer getIrriAreaId() {
        return irriAreaId;
    }

    public void setIrriAreaId(Integer irriAreaId) {
        this.irriAreaId = irriAreaId;
    }

    public Integer getExpDuration() {
        return expDuration;
    }

    public void setExpDuration(Integer expDuration) {
        this.expDuration = expDuration;
    }

    public Integer getIrriDuration() {
        return irriDuration;
    }

    public void setIrriDuration(Integer irriDuration) {
        this.irriDuration = irriDuration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public UserBasicInfoVo getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserBasicInfoVo createUser) {
        this.createUser = createUser;
    }

    public UserBasicInfoVo getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(UserBasicInfoVo deleteUser) {
        this.deleteUser = deleteUser;
    }

    public NormalIrriTaskResult getResult() {
        return result;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Date getIrriStartTime() {
        return irriStartTime;
    }

    public void setIrriStartTime(Date irriStartTime) {
        this.irriStartTime = irriStartTime;
    }

    public String getFarmlandName() {
        return farmlandName;
    }

    public void setFarmlandName(String farmlandName) {
        this.farmlandName = farmlandName;
    }

    public String getIrriAreaName() {
        return irriAreaName;
    }

    public void setIrriAreaName(String irriAreaName) {
        this.irriAreaName = irriAreaName;
    }
}
