package com.zzwl.ias.controller.admin.iasystem;

import com.github.pagehelper.PageInfo;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.UserAddDTO;
import com.zzwl.ias.dto.UserQueryDTO;
import com.zzwl.ias.dto.UserUpdateDTO;
import com.zzwl.ias.dto.iasystem.AddUserAndIaSystemDTO;
import com.zzwl.ias.dto.iasystem.UserAddIaSystemQueryDTO;
import com.zzwl.ias.service.UserService;
import com.zzwl.ias.vo.RoleVo;
import com.zzwl.ias.vo.UserAddIaSystemVo;
import com.zzwl.ias.vo.UserDetailVo;
import com.zzwl.ias.vo.UserListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Lvpin on 2018/11/16.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    /**
     * 用户列表
     *
     * @return
     */
    @RequestMapping(path = "/get_user_list", method = RequestMethod.GET)
    public Object getUserList(UserQueryDTO dto) {
        PageInfo<UserListVo> userListVos = userService.selectUserList(dto);
        return Result.ok(userListVos);
    }

    /**
     * 用户详情（修改）
     */
    @RequestMapping(path = "/get_user_detail", method = RequestMethod.GET)
    public Object getUserDetail(@RequestParam("id") Integer id) {
        List<UserDetailVo> userDetailVos = userService.selectUserById(id);
        return Result.ok(userDetailVos);
    }

    /**
     * 用户关联系统的列表
     */
    @RequestMapping(path = "/iasystem_in_user/{userId}", method = RequestMethod.GET)
    public Object getIaSystemInUserList(@PathVariable("userId") Integer id, UserAddIaSystemQueryDTO userAddIaSystemQueryDTO) {
        userAddIaSystemQueryDTO.setUserId(id);
        PageInfo<UserAddIaSystemVo> userAddIaSystemVos = userService.selectIaSystemInUser(userAddIaSystemQueryDTO);
        return Result.ok(userAddIaSystemVos);
    }

    /**
     * 用户绑定系统
     */
    @RequestMapping(path = "/add_iasystem", method = RequestMethod.PUT)
    public Object addIasystem(AddUserAndIaSystemDTO addUserAndIaSystemDTO) {
        Integer integer = userService.insertUserAndIaSystem(addUserAndIaSystemDTO);
        if (integer != 0) {
            return Result.error(ErrorCode.SYSTEM_ALREADY_BOUND);
        }
        return Result.ok();
    }

    /**
     * 添加用户
     */
    @RequestMapping(path = "/add_user", method = RequestMethod.PUT)
    public Object addUser(@Validated UserAddDTO userAddDTO) {
        int count = userService.insertUser(userAddDTO);
        if (count != 0) {
            return Result.error(ErrorCode.USER_EXIST);
        }
        return Result.ok();
    }

    /**
     * 修改用户
     */
    @RequestMapping(path = "/update_user", method = RequestMethod.POST)
    public Object updateUser(@Validated UserUpdateDTO userUpdateDTO) {
        int count = userService.userUpdate(userUpdateDTO);
        if (count != 0) {
            return Result.error(ErrorCode.USER_EXIST);
        }
        return Result.ok();
    }

    /**
     * 用户详情（查看）
     *
     * @param userId
     * @return
     */
    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public Object getUserDetailLook(@PathVariable Integer userId) {
        UserListVo userListVo = userService.userLookDetail(userId);
        return Result.ok(userListVo);
    }

    /**
     * 用户删除
     *
     * @param userId
     * @return
     */
    @RequestMapping(path = "/{userId}", method = RequestMethod.POST)
    public Object deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return Result.ok();
    }

    /**
     * 用户解绑系统
     *
     * @param userId
     * @param iasId
     * @return
     */
    @RequestMapping(path = "/iasystem/{userId}/{iasId}", method = RequestMethod.DELETE)
    public Object deleteUserAndIaSystem(@PathVariable Integer userId, @PathVariable Integer iasId) {
        userService.deleteUserAndIaSystem(userId, iasId);
        return Result.ok();
    }

    /**
     * 角色列表
     *
     * @return
     */
    @RequestMapping(path = "/role", method = RequestMethod.GET)
    public Object getRole() {
        List<RoleVo> roleVos = userService.selectRole();
        return Result.ok(roleVos);
    }

}
