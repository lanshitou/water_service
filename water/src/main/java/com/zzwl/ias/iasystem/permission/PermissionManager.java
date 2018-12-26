package com.zzwl.ias.iasystem.permission;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.IasDeviceRecord;
import com.zzwl.ias.domain.UserFarmlandRecord;
import com.zzwl.ias.domain.UserIasRecord;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.constant.PermissionConstant;
import com.zzwl.ias.mapper.IasDeviceRecordExtMapper;
import com.zzwl.ias.mapper.UserFarmlandRecordExtMapper;
import com.zzwl.ias.mapper.UserIasRecordExtMapper;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-23
 * Time: 21:01
 */
@Component
public class PermissionManager {
    public Integer getUserRoleOnIas(int userId, int iasId) {
        UserIasRecord iasRecord = AppContext.getBean(UserIasRecordExtMapper.class).selectByUserAndIas(userId, iasId);
        if (iasRecord == null) {
            return PermissionConstant.IASYSTEM_USER_INVALID;
        }
        return iasRecord.getRoleId();
    }

    public List<Integer> listIasUser(int iasId) {
        List<UserIasRecord> userIasRecords = AppContext.getBean(UserIasRecordExtMapper.class).selectByIasId(iasId);
        LinkedList<Integer> users = new LinkedList<>();
        for (UserIasRecord userIasRecord : userIasRecords) {
            users.add(userIasRecord.getUserId());
        }
        return users;
    }

    /**
     * 查找拥有指定农田管理权限的用户
     *
     * @param iasId      智慧农业系统ID
     * @param farmlandId 农田ID
     * @return 用户ID列表
     */
    public List<Integer> listFarmlandUser(int iasId, int farmlandId) {
        LinkedList<Integer> users = new LinkedList<>();

        //获取智慧农业系统的拥有者和管理员
        List<UserIasRecord> userIasRecords = AppContext.getBean(UserIasRecordExtMapper.class).selectByIasId(iasId);
        for (UserIasRecord userIasRecord : userIasRecords) {
            if (userIasRecord.getRoleId() != PermissionConstant.IASYSTEM_USER_RETRIEVE) {
                users.add(userIasRecord.getUserId());
            }
        }
        //获取智慧农业系统农田的租用者
        List<UserFarmlandRecord> userFarmlandRecords = AppContext.getBean(UserFarmlandRecordExtMapper.class).selectByFarmlandId(farmlandId);
        for (UserFarmlandRecord userFarmlandRecord : userFarmlandRecords) {
            users.add(userFarmlandRecord.getUserId());
        }

        return users;
    }

    /**
     * 获取用户传感器列表
     *
     * @param userId 用户ID
     * @return 传感器列表
     */
    public List<Integer> listUserSensor(Integer userId) {
        LinkedList<Integer> sensors = new LinkedList<>();
        List<UserIasRecord> userIasRecords = AppContext.getBean(UserIasRecordExtMapper.class).selectByUserId(userId);
        for (UserIasRecord userIasRecord : userIasRecords) {
            List<IasDeviceRecord> iasDeviceRecords =
                    AppContext.getBean(IasDeviceRecordExtMapper.class).selectByIasId(userIasRecord.getIasId());
            if (userIasRecord.getRoleId() != PermissionConstant.IASYSTEM_USER_RETRIEVE) {
                //用户是智慧农业系统的拥有者或者管理员，则可以获取到所有的传感器信息
                for (IasDeviceRecord iasDeviceRecord : iasDeviceRecords) {
                    if (DeviceAddr.isSensor(iasDeviceRecord.getDevId())) {
                        sensors.add(iasDeviceRecord.getId());
                    }
                }
            } else {
                //用户是智慧农业系统的租用者，则只能获取到公用传感器和其拥有的农田中的传感器信息
                List<UserFarmlandRecord> userFarmlandRecords =
                        AppContext.getBean(UserFarmlandRecordExtMapper.class).selectByIasIdAndUserId(userIasRecord.getIasId(), userId);
                for (IasDeviceRecord iasDeviceRecord : iasDeviceRecords) {
                    if (!DeviceAddr.isSensor(iasDeviceRecord.getDevId())) {
                        continue;
                    }
                    if (iasDeviceRecord.getFarmlandId() == null) {
                        //公用设备
                        sensors.add(iasDeviceRecord.getId());
                    } else {
                        //农田中的设备
                        for (UserFarmlandRecord userFarmlandRecord : userFarmlandRecords) {
                            if (userFarmlandRecord.getFmId().equals(iasDeviceRecord.getFarmlandId())) {
                                sensors.add(iasDeviceRecord.getId());
                            }
                        }
                    }
                }
            }
        }
        return sensors;
    }


    /** 获取用户在指定智慧农业系统上的权限
     * @param iasId 智慧农业系统ID
     * @param userId 用户ID
     * @return 用户在指定智慧农业系统上的权限。如果用户没有权限则抛出异常
     */
    public UserIasPermission getUserIasPermission(int iasId, int userId) {
        UserIasPermission userIasPermission = new UserIasPermission();
        UserIasRecord userIasRecord =
                AppContext.getBean(UserIasRecordExtMapper.class).selectByUserAndIas(userId, iasId);
        AssertEx.isTrue(userIasRecord != null, ErrorCode.IASYSTEM_NOT_EXIST);

        userIasPermission.setRole(userIasRecord.getRoleId());
        if (userIasRecord.getRoleId() == PermissionConstant.IASYSTEM_USER_RETRIEVE) {
            LinkedList<Integer> farmlands = new LinkedList<>();
            List<UserFarmlandRecord> userFarmlandRecords =
                    AppContext.getBean(UserFarmlandRecordExtMapper.class).selectByIasIdAndUserId(iasId, userId);
            for (UserFarmlandRecord userFarmlandRecord : userFarmlandRecords) {
                farmlands.add(userFarmlandRecord.getFmId());
            }
            userIasPermission.setFarmlands(farmlands);
            return userIasPermission;
        } else {
            return userIasPermission;
        }
    }


    /** 获取用户的所有的智慧农业系统权限
     * @param userId 用户ID
     * @return 用户智慧农业系统权限列表
     */
    public LinkedList<UserIasPermission> listUserIasPermission(int userId){
        List<UserIasRecord> userIasRecords = AppContext.getBean(UserIasRecordExtMapper.class)
                .selectByUserId(userId);
        LinkedList<UserIasPermission> userIasPermissions = new LinkedList<>();
        for (UserIasRecord userIasRecord : userIasRecords){
            UserIasPermission userIasPermission = new UserIasPermission();
            userIasPermission.setIasId(userIasRecord.getIasId());
            userIasPermission.setRole(userIasRecord.getRoleId());
            if (userIasRecord.getRoleId() == PermissionConstant.IASYSTEM_USER_RETRIEVE){
                LinkedList<Integer> farmlands = new LinkedList<>();
                List<UserFarmlandRecord> userFarmlandRecords =
                        AppContext.getBean(UserFarmlandRecordExtMapper.class).selectByIasIdAndUserId(userIasRecord.getIasId(), userId);
                for (UserFarmlandRecord userFarmlandRecord : userFarmlandRecords){
                    farmlands.add(userFarmlandRecord.getFmId());
                }
                userIasPermission.setFarmlands(farmlands);
            }
            userIasPermissions.add(userIasPermission);
        }
        return userIasPermissions;
    }

    public UserIasPermissionSet getUserIasPermissionSet(int userId){
        UserIasPermissionSet userIasPermissionSet = new UserIasPermissionSet();
        LinkedList<UserIasPermission> userIasPermissions = listUserIasPermission(userId);
        if (userIasPermissions != null) {
            LinkedList<Integer> allPermissionIas = new LinkedList<>();
            LinkedList<Integer> retrievePermissionIas = new LinkedList<>();
            LinkedList<Integer> farmlands = new LinkedList<>();
            for (UserIasPermission userIasPermission : userIasPermissions) {
                if (userIasPermission.getRole() == PermissionConstant.IASYSTEM_USER_RETRIEVE) {
                    retrievePermissionIas.add(userIasPermission.getIasId());
                    farmlands.addAll(userIasPermission.getFarmlands());
                } else {
                    allPermissionIas.add(userIasPermission.getIasId());
                }
            }
            if (allPermissionIas.size() == 0) {
                allPermissionIas = null;
            }

            if (retrievePermissionIas.size() == 0) {
                retrievePermissionIas = null;
            }

            if (farmlands.size() == 0) {
                farmlands = null;
            }
            if (allPermissionIas == null && retrievePermissionIas == null && farmlands == null){
                return null;
            }

            userIasPermissionSet.setAdminPermissionIas(allPermissionIas);
            userIasPermissionSet.setRetrievePermissionIas(retrievePermissionIas);
            userIasPermissionSet.setFarmlands(farmlands);
        }
        return userIasPermissionSet;
    }
}
