package com.zzwl.ias.service;

import com.github.pagehelper.PageInfo;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.User;
import com.zzwl.ias.dto.UserAddDTO;
import com.zzwl.ias.dto.UserQueryDTO;
import com.zzwl.ias.dto.UserUpdateDTO;
import com.zzwl.ias.dto.iasystem.AddUserAndIaSystemDTO;
import com.zzwl.ias.dto.iasystem.UserAddIaSystemQueryDTO;
import com.zzwl.ias.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by HuXin on 2017/12/8.
 */
@Component
@Mapper
public interface UserService {
    ErrorCode register(String mobile, String passWord, String verifyCode, String nickName);

    ErrorCode sendRegisterVerificationCode(String mobile);

    ErrorCode changePassword(String oldPassword, String newPassword);

    ErrorCode changePassword(String mobile, String password, String verifyCode);

    ErrorCode changeMobile(Integer currentId, String mobile, String verifyCode);

    ErrorCode sendChangePassCode(String mobile);

    ErrorCode checkMobile(String mobile, String verifyCode, int vcodeType);

    ErrorCode updateUserInfo(Integer currentId, UserVo userVo);

    User findUserByUserId(Integer userId);

    User findByUserName(String userName);

    User findByMobile(String mobile);

    Set<String> findRoles(String userName);

    Set<String> findPermissions(String userName);

    String getAgreement();

    /**
     * 用户列表
     *
     * @param dto
     * @return
     */
    PageInfo<UserListVo> selectUserList(UserQueryDTO dto);

    /**
     * 用户详情（修改）
     */
    List<UserDetailVo> selectUserById(Integer id);

    /**
     * 用户关联系统的列表
     */
    PageInfo<UserAddIaSystemVo> selectIaSystemInUser(UserAddIaSystemQueryDTO userAddIaSystemQueryDTO);

    /**
     * 用户绑定系统
     */
    int insertUserAndIaSystem(AddUserAndIaSystemDTO addUserAndIaSystemDTO);

    /**
     * 添加用户
     *
     * @param userAddDTO
     * @return
     */
    int insertUser(UserAddDTO userAddDTO);

    /**
     * 修改用户
     *
     * @param userUpdateDTO
     * @return
     */
    int userUpdate(UserUpdateDTO userUpdateDTO);

    /**
     * 用户详情（查看）
     *
     * @param userId
     * @return
     */
    UserListVo userLookDetail(Integer userId);

    /**
     * 用户删除
     *
     * @param userId
     */
    void deleteUser(Integer userId);

    /**
     * 用户解绑系统
     *
     * @param userId
     * @param iasId
     */
    void deleteUserAndIaSystem(Integer userId, Integer iasId);

    /**
     * 角色列表
     *
     * @return
     */
    List<RoleVo> selectRole();
}
