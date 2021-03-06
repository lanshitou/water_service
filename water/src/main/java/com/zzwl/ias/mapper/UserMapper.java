package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.User;
import com.zzwl.ias.dto.UserAddDTO;
import com.zzwl.ias.dto.UserQueryDTO;
import com.zzwl.ias.dto.UserUpdateDTO;
import com.zzwl.ias.dto.iasystem.UserAddIaSystemQueryDTO;
import com.zzwl.ias.vo.RoleVo;
import com.zzwl.ias.vo.UserAddIaSystemVo;
import com.zzwl.ias.vo.UserDetailVo;
import com.zzwl.ias.vo.UserListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(User record);

    User selectByUserId(Integer id);

    User selectByUserName(String name);

    User selectByMobile(String mobile);

    int updateMobileByUserId(@Param(value = "userId") Integer userId, @Param(value = "mobile") String mobile);

    /**
     * 用户列表
     *
     * @return
     */
    List<UserListVo> selectUserList(UserQueryDTO dto);

    /**
     * 用户详情（修改）
     */
    List<UserDetailVo> selectUserById(@Param("id") Integer id);

    /**
     * 用户关联系统的列表
     */
    List<UserAddIaSystemVo> selectIaSystemInUser(UserAddIaSystemQueryDTO userAddIaSystemQueryDTO);

    /**
     * 用户绑定系统
     */
    void insertUserAndIaSystem(List<Map> maps);

    /**
     * 判断是否已绑定
     */
    int countUserAndIaSystem(@Param("userId") Integer userId, @Param("iaSystemId") Integer iaSystemId);

    /**
     * 判断用户是否存在
     *
     * @param idCard
     * @return
     */
    int countUserByIdCard(@Param("idCard") String idCard);

    /**
     * 添加用户
     *
     * @param userAddDTO
     */
    void insertUser(UserAddDTO userAddDTO);

    /**
     * 修改用户
     *
     * @param userUpdateDTO
     */
    void userUpdate(UserUpdateDTO userUpdateDTO);

    /**
     * 用户详情（查看）
     *
     * @param userId
     * @return
     */
    UserListVo userLookDetail(@Param("userId") Integer userId);

    /**
     * 用户删除
     *
     * @param userId
     */
    void deleteUser(@Param("userId") Integer userId);

    /**
     * 用户解绑系统
     *
     * @param userId
     * @param iasId
     */
    void deleteUserAndIaSystem(@Param("userId") Integer userId, @Param("iasId") Integer iasId);

    /**
     * 角色列表
     *
     * @return
     */
    List<RoleVo> selectRole();

    /**
     * 按身份证号查找用户
     *
     * @param idCard
     * @return
     */
    List<UserDetailVo> selectUserByIdCard(@Param("idCard") String idCard);

    /**
     * 根据地区查找用户id集合
     *
     * @param addressId
     * @return
     */
    List<Integer> selectUserIdsByAddressId(@Param("addressId") Integer addressId);
}