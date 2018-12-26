package com.zzwl.ias.dto.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.DeviceOperateDO;
import com.zzwl.ias.vo.UserBasicInfoVo;

/**
 * Description:
 * User: HuXin
 * Date: 2018-10-12
 * Time: 13:49
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeviceOperateDTO extends DeviceOperateDO{
    private UserBasicInfoVo user;

    public UserBasicInfoVo getUser() {
        return user;
    }

    public void setUser(UserBasicInfoVo user) {
        this.user = user;
    }
}
