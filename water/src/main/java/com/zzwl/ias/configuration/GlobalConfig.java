package com.zzwl.ias.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.SysConfigDO;
import com.zzwl.ias.mapper.SysConfigDoExtMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-26
 * Time: 15:27
 */
@Component
public class GlobalConfig {
    private HashMap<String, String> configs;
    private ObjectMapper objectMapper;

    public GlobalConfig() {
        configs = new HashMap<>();
        objectMapper = new ObjectMapper();
    }

    public void load() {
        try {
            List<SysConfigDO> sysConfigDOS = AppContext.getBean(SysConfigDoExtMapper.class).getAllConfig();
            for (SysConfigDO sysConfigDO : sysConfigDOS) {
                configs.put(sysConfigDO.getName(), (String) sysConfigDO.getValue());
            }
        } catch (Exception e) {
            //TODO
        }
    }

//    public String getConfig1(String key) {
//        return configs.get(key);
//    }

    public <T> T getConfig(String key, Class<T> tClass) {
        T result;
        try {
            String value = configs.get(key);
            if (value == null) {
                return null;
            }
            result = objectMapper.readValue(value, tClass);
        } catch (IOException e) {
            //TODO
            result = null;
        }
        return result;
    }

    public ErrorCode setConfig(String key, Object target) {
        try {
            String value = objectMapper.writeValueAsString(target);
            SysConfigDO sysConfigDO = new SysConfigDO();
            sysConfigDO.setName(key);
            sysConfigDO.setValue(value);
            try {
                if (configs.containsKey(key)) {
                    AppContext.getBean(SysConfigDoExtMapper.class).updateByPrimaryKey(sysConfigDO);
                } else {
                    AppContext.getBean(SysConfigDoExtMapper.class).insert(sysConfigDO);
                }
                configs.put(key, value);
                return ErrorCode.OK;
            } catch (Exception e) {
                return ErrorCode.SET_GLOBAL_CONFIG_FAIL;
            }
        } catch (JsonProcessingException e) {
            return ErrorCode.SET_GLOBAL_CONFIG_FAIL;
        }
    }

//    private ErrorCode setConfig(String key, String value){
//        SysConfigDO sysConfigDO = new SysConfigDO();
//        sysConfigDO.setName(key);
//        sysConfigDO.setValue(value);
//        try {
//            if (configs.containsKey(key)) {
//                AppContext.getBean(SysConfigDoExtMapper.class).updateByPrimaryKey(sysConfigDO);
//            } else {
//                AppContext.getBean(SysConfigDoExtMapper.class).insert(sysConfigDO);
//            }
//            configs.put(key, value);
//            return ErrorCode.OK;
//        }
//        catch (Exception e){
//            return ErrorCode.SET_GLOBAL_CONFIG_FAIL;
//        }
//    }
}
