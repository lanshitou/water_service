package com.zzwl.ias.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzwl.ias.common.CommonUtil;
import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.User;
import com.zzwl.ias.domain.VerificationRecord;
import com.zzwl.ias.domain.VerificationRecordKey;
import com.zzwl.ias.domain.VerificationRecordType;
import com.zzwl.ias.dto.UserAddDTO;
import com.zzwl.ias.dto.UserQueryDTO;
import com.zzwl.ias.dto.UserUpdateDTO;
import com.zzwl.ias.dto.iasystem.AddUserAndIaSystemDTO;
import com.zzwl.ias.dto.iasystem.UserAddIaSystemQueryDTO;
import com.zzwl.ias.mapper.UserMapper;
import com.zzwl.ias.mapper.VerificationRecordMapper;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.service.SmsService;
import com.zzwl.ias.service.UserService;
import com.zzwl.ias.vo.*;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.zzwl.ias.common.RoleType.IAS_ADMIN;

/**
 * Created by HuXin on 2017/12/8.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final int VCODE_SEND_INTERVAL = 180 * 1000;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private VerificationRecordMapper verificationRecordMapper;

    @Autowired
    private SmsService smsService;
    @Autowired
    private IaSystemService iaSystemService;

    @Override
    public ErrorCode register(String mobile, String password, String verifyCode, String nickName) {
        //检查昵称格式
        if (!CommonUtil.isUsername(nickName)) {
            return ErrorCode.INVALID_USERNAME;
        }
        //检查手机号码
        if (!CommonUtil.isMobile(mobile)) {
            return ErrorCode.INVALID_MOBILE;
        }
        //检查密码
        if (!CommonUtil.isPassword(password)) {
            return ErrorCode.INVALID_PASSWORD;
        }
        //检查用户是否存在
        if (userMapper.selectByMobile(mobile) != null) {
            return ErrorCode.USER_EXIST;
        }
        //检查验证码
        VerificationRecordKey verificationRecordKey = new VerificationRecordKey();
        verificationRecordKey.setMobile(mobile);
        verificationRecordKey.setType(VerificationRecordType.VCODE_TYPE_REGISTER);
        VerificationRecord verificationRecord = verificationRecordMapper.selectByPrimaryKey(verificationRecordKey);
        if (verificationRecord == null ||
                Calendar.getInstance().getTimeInMillis() - verificationRecord.getSendTime().getTime() > VCODE_SEND_INTERVAL ||
                verificationRecord.getCode().compareTo(verifyCode) != 0) {
            return ErrorCode.INVALID_VCODE;
        }

        //注册用户
        User user = new User();
        user.setUsername(nickName);
        user.setMobile(mobile);
        user.setPasswordBackup(password);
        user.setPassword(passwordService.encryptPassword(password));
        userMapper.insert(user);

        return ErrorCode.OK;
    }

    @Override
    public ErrorCode sendRegisterVerificationCode(String mobile) {
        //检查用户是否已经存在
        User user = userMapper.selectByMobile(mobile);
        if (user != null) {
            return ErrorCode.USER_EXIST;
        }

        return sendVerificationCode(mobile, VerificationRecordType.VCODE_TYPE_REGISTER);
    }

    @Override
    public ErrorCode changePassword(String oldPassword, String newPassword) {
        Integer currentId = CurrentUserUtil.getCurrentUserId();
        //检验旧密码是否正确
        User user = userMapper.selectByPrimaryKey(currentId);
        if (!user.getPasswordBackup().equals(oldPassword)) {
            return ErrorCode.INVALID_OLD_PASSWORD;
        }
        //检查新密码
        if (!CommonUtil.isPassword(newPassword)) {
            return ErrorCode.INVALID_PASSWORD;
        }
        //修改用户密码
        user.setPasswordBackup(newPassword);
        user.setPassword(passwordService.encryptPassword(newPassword));
        userMapper.updateByPrimaryKey(user);
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode changePassword(String mobile, String password, String verifyCode) {
        //检查手机号码
        if (!CommonUtil.isMobile(mobile)) {
            return ErrorCode.INVALID_MOBILE;
        }
        //检查密码
        if (!CommonUtil.isPassword(password)) {
            return ErrorCode.INVALID_PASSWORD;
        }
        //检查用户是否存在
        User user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return ErrorCode.USER_NOT_EXIST;
        }
        //检查验证码
        if (checkMobile(mobile, verifyCode, VerificationRecordType.VCODE_TYPE_CHANGE_PASS) != ErrorCode.OK) {
            return ErrorCode.INVALID_VCODE;
        }

        //修改用户密码
        user.setPasswordBackup(password);
        user.setPassword(passwordService.encryptPassword(password));
        userMapper.updateByPrimaryKey(user);

        return ErrorCode.OK;
    }

    @Override
    public ErrorCode changeMobile(Integer currentId, String mobile, String verifyCode) {
        //检查手机号码
        if (!CommonUtil.isMobile(mobile)) {
            return ErrorCode.INVALID_MOBILE;
        }
        //检查手机号码是否已被使用
        User user = userMapper.selectByMobile(mobile);
        if (user != null) {
            return ErrorCode.MOBILE_EXIST;
        }
        //检查验证码
        if (checkMobile(mobile, verifyCode, VerificationRecordType.VCODE_TYPE_REGISTER) != ErrorCode.OK) {
            return ErrorCode.INVALID_VCODE;
        }
        //修改手机号
        userMapper.updateMobileByUserId(currentId, mobile);
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode sendChangePassCode(String mobile) {
        //检查用户是否存在
        User user = userMapper.selectByMobile(mobile);
        if (user == null) {
            return ErrorCode.USER_NOT_EXIST;
        }

        return sendVerificationCode(mobile, VerificationRecordType.VCODE_TYPE_CHANGE_PASS);
    }

    @Override
    public ErrorCode checkMobile(String mobile, String verifyCode, int vcodeType) {
        //检查验证码
        VerificationRecordKey verificationRecordKey = new VerificationRecordKey();
        verificationRecordKey.setMobile(mobile);
        verificationRecordKey.setType(vcodeType);
        VerificationRecord verificationRecord = verificationRecordMapper.selectByPrimaryKey(verificationRecordKey);
        if (verificationRecord == null ||
                Calendar.getInstance().getTimeInMillis() - verificationRecord.getSendTime().getTime() > VCODE_SEND_INTERVAL ||
                verificationRecord.getCode().compareTo(verifyCode) != 0) {
            return ErrorCode.INVALID_VCODE;
        }
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode updateUserInfo(Integer currentId, UserVo userVo) {
        User user = new User();
        user.setId(currentId);
        if (userVo.getNickName() != null && !userVo.getNickName().equals("")) {
            //检查昵称格式
            if (!CommonUtil.isUsername(userVo.getNickName())) {
                return ErrorCode.INVALID_USERNAME;
            }
            user.setUsername(userVo.getNickName());
        }
        if (userVo.getPlantingInfoVo() != null) {
            user.setArea(userVo.getPlantingInfoVo().getArea());
            user.setCrop(userVo.getPlantingInfoVo().getCrop());
            user.setPerMuYield(userVo.getPlantingInfoVo().getPerMuYield());
        }
        userMapper.updateByPrimaryKeySelective(user);
        return ErrorCode.OK;
    }

    private ErrorCode sendVerificationCode(String mobile, int type) {
        //检查是否已经发送过验证码
        VerificationRecordKey verificationRecordKey = new VerificationRecordKey();
        verificationRecordKey.setMobile(mobile);
        verificationRecordKey.setType(type);
        VerificationRecord verificationRecord = verificationRecordMapper.selectByPrimaryKey(verificationRecordKey);
        if (verificationRecord != null) {
            if (Calendar.getInstance().getTimeInMillis() - verificationRecord.getSendTime().getTime() < VCODE_SEND_INTERVAL) {
                return ErrorCode.VCODE_ALREADY_SENT;
            }
        } else {
            verificationRecord = new VerificationRecord();
            verificationRecord.setMobile(mobile);
            verificationRecord.setType(type);
        }

        //发送验证码
        verificationRecord.setCode(CommonUtil.generateVerificationCode());
        verificationRecord.setSendTime(Calendar.getInstance().getTime());
        ErrorCode errorCode;
        if (type == VerificationRecordType.VCODE_TYPE_REGISTER) {
            errorCode = smsService.sendRegisterVerificationCode(mobile, verificationRecord.getCode());
        } else {
            errorCode = smsService.sendChangePassCode(mobile, verificationRecord.getCode());
        }

        if (errorCode == ErrorCode.OK) {
            verificationRecordMapper.upsert(verificationRecord);
        }

        return errorCode;
    }

    @Override
    public User findUserByUserId(Integer userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public User findByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public User findByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public Set<String> findRoles(String userName) {
        return null;
    }

    @Override
    public Set<String> findPermissions(String userName) {
        return null;
    }

    @Override
    public String getAgreement() {
        String content = "<html>    <h1 class=\"flex-center\" padding>用户服务协议</h1>\n" +
                "    <h2>一、特别提示 </h2>\n" +
                "    <p>\n" +
                "        在此特别提醒您（用户）在注册成为众智新农用户之前，请认真阅读本《众智新农用户服务协议》（以下简称“协议”），确保您充分理解本协议中各条款。请您审慎阅读并选择接受或不接受本协议。除非您接受本协议所有条款，否则您无权注册、登陆或使用本协议所涉服务。您的注册、登陆、使用等行为将视为对本协议的接受，并同意接受本协议各项条款的约束。\n" +
                "        本协议约定众智新农与用户之间关于“众智新农”服务（以下简称“服务”）的权利义务。“用户”是指注册、登陆、使用本服务的个人。本协议可由众智新农随时更新，更新后的协议条款一旦公布即代替原来的协议条款，恕不再另行通知，用户可在本APP中查阅最新版协议条款。在修改协议条款后，如果用户不接受修改后的条款，请立即停止使用众智新农提供的服务，用户继续使用众智新农提供的服务将被视为接受修改后的协议。\n" +
                "    </p>\n" +
                "    <h2>二、账号注册</h2>\n" +
                "    <p>\n" +
                "        1、用户在使用本服务前需要注册一个“众智新农”账号。“众智新农”账号应当使用手机号码绑定注册，请用户使用尚未与“众智新农”账号绑定的手机号码，以及未被众智新农根据本协议封禁的手机号码注册“众智新农”账号。众智新农可以根据用户需求或产品需要对账号注册和绑定的方式进行变更，而无须事先通知用户。\n" +
                "        2、如果注册申请者有被众智新农封禁的先例或涉嫌虚假注册及滥用他人名义注册，及其他不能得到许可的理由， 众智新农将拒绝其注册申请。\n" +
                "        3、鉴于“众智新农”账号的绑定注册方式，您同意众智新农在注册时将允许您的手机号码及手机设备识别码等信息用于注册。\n" +
                "        4、在用户注册及使用本服务时，众智新农需要搜集能识别用户身份的个人信息以便众智新农可以在必要时联系用户，或为用户提供更好的使用体验。众智新农搜集的信息包括但不限于用户的姓名、地址；众智新农同意对这些信息的使用将受限于第三条用户个人隐私信息保护的约束。\n" +
                "    </p>\n" +
                "    <h2>三、账户安全</h2>\n" +
                "    <p>\n" +
                "        1、用户一旦注册成功，成为众智新农的用户，将得到一个用户名和密码，并有权利使用自己的用户名及密码随时登陆众智新农。\n" +
                "        2、用户对用户名和密码的安全负全部责任，同时对以其用户名进行的所有活动和事件负全责。\n" +
                "        3、用户不得以任何形式擅自转让或授权他人使用自己的众智新农用户名。\n" +
                "        4、如果用户泄漏了密码，有可能导致不利的法律后果，因此不管任何原因导致用户的密码安全受到威胁，应该立即和众智新农客服人员取得联系，否则后果自负。\n" +
                "    </p>\n" +
                "    <h2>四、用户声明与保证</h2>\n" +
                "    <p>\n" +
                "        1、用户承诺其为具有完全民事行为能力的民事主体，且具有达成交易履行其义务的能力。\n" +
                "        2、用户有义务在注册时提供自己的真实资料，并保证诸如手机号码、姓名、所在地区等内容的有效性及安全性，保证众智新农工作人员可以通过上述联系方式与用户取得联系。同时，用户也有义务在相关资料实际变更时及时更新有关注册资料。\n" +
                "        3、用户通过使用众智新农的过程中所制作、上载、复制、发布、传播的任何内容，包括但不限于账号头像、名称、用户说明等注册信息及认证资料，或文字、语音、图片、视频、图文等发送、回复和相关链接页面，以及其他使用账号或本服务所产生的内容，不得违反国家相关法律制度，包含但不限于如下原则：\n" +
                "        （1）反对宪法所确定的基本原则的；\n" +
                "        （2）危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；\n" +
                "        （3）损害国家荣誉和利益的；\n" +
                "        （4）煽动民族仇恨、民族歧视，破坏民族团结的；\n" +
                "        （5）破坏国家宗教政策，宣扬邪教和封建迷信的；\n" +
                "        （6）散布谣言，扰乱社会秩序，破坏社会稳定的；\n" +
                "        （7）散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；\n" +
                "        （8）侮辱或者诽谤他人，侵害他人合法权益的；\n" +
                "        （9）含有法律、行政法规禁止的其他内容的。\n" +
                "        4、用户不得利用“众智新农”账号或本服务制作、上载、复制、发布、传播下干扰“众智新农”正常运营，以及侵犯其他用户或第三方合法权益的内容：\n" +
                "        （1）含有任何性或性暗示的；\n" +
                "        （2）含有辱骂、恐吓、威胁内容的；\n" +
                "        （3）含有骚扰、垃圾广告、恶意信息、诱骗信息的；\n" +
                "        （4）涉及他人隐私、个人信息或资料的；\n" +
                "        （5）侵害他人名誉权、肖像权、知识产权、商业秘密等合法权利的；\n" +
                "        （6）含有其他干扰本服务正常运营和侵犯其他用户或第三方合法权益内容的信息。\n" +
                "    </p>\n" +
                "    <h2>六、服务的终止</h2>\n" +
                "    <p>\n" +
                "        1、在下列情况下，众智新农有权终止向用户提供服务：\n" +
                "        （1）在用户违反本服务协议相关规定时，众智新农有权终止向该用户提供服务。如该用户再一次直接或间接或以他人名义注册为用户的，一经发现，众智新农有权直接单方面终止向该用户提供服务；\n" +
                "        （2）如众智新农通过用户提供的信息与用户联系时，发现用户在注册时填写的联系方式已不存在或无法接通，众智新农以其它联系方式通知用户更改，而用户在三个工作日内仍未能提供新的联系方式，众智新农有权终止向该用户提供服务；\n" +
                "        （3）用户不得通过程序或人工方式进行刷量或作弊，若发现用户有作弊行为，众智新农将立即终止服务，并有权扣留账户内金额；\n" +
                "        （4）一旦众智新农发现用户提供的数据或信息中含有虚假内容，众智新农有权随时终止向该用户提供服务；\n" +
                "        （5）本服务条款终止或更新时，用户明示不愿接受新的服务条款；\n" +
                "        （6）其它众智新农认为需终止服务的情况。\n" +
                "    </p></html>";
        return content;
    }

    @Override
    public PageInfo<UserListVo> selectUserList(UserQueryDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<UserListVo> userListVos = userMapper.selectUserList(dto);
        return new PageInfo<>(userListVos);
    }

    @Override
    public List<UserDetailVo> selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public PageInfo<UserAddIaSystemVo> selectIaSystemInUser(UserAddIaSystemQueryDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<UserAddIaSystemVo> userAddIaSystemVos = userMapper.selectIaSystemInUser(dto);
        return new PageInfo<>(userAddIaSystemVos);
    }

    @Override
    @Transactional
    public int insertUserAndIaSystem(AddUserAndIaSystemDTO addUserAndIaSystemDTO) {
        List<Integer> iaSystemIds = addUserAndIaSystemDTO.getIaSystemIds();
        int count = 0;
        for (Integer iaSystemId : iaSystemIds) {
            count = userMapper.countUserAndIaSystem(addUserAndIaSystemDTO.getUserId(), iaSystemId);
        }
        if (count == 0) {
            List<Map> maps = new ArrayList<>();
            List<Integer> iasIds = new ArrayList<>();
            for (int i = 0; i < addUserAndIaSystemDTO.getIaSystemIds().size(); i++) {
                HashMap<String, Object> data = new HashMap<>();
                List<Integer> iaSystemIds1 = addUserAndIaSystemDTO.getIaSystemIds();
                List<Integer> roleIds = addUserAndIaSystemDTO.getRoleIds();
                data.put("userId", addUserAndIaSystemDTO.getUserId());
                data.put("iaSystemId", iaSystemIds1.get(i));
                data.put("roleId", roleIds.get(i));
                maps.add(data);
                //如果权限是管理员，则对该系统下的所有农田对用户进行绑定
                if (IAS_ADMIN.getValue() == roleIds.get(i)) {
                    iasIds.add(iaSystemIds1.get(i));
                }
            }
            if (iasIds.size() > 0) {
                for (Integer iasId : iasIds) {
                    iaSystemService.updateIaSystemRole(addUserAndIaSystemDTO.getUserId(), IAS_ADMIN.getValue(), iasId);
                }
            }
            userMapper.insertUserAndIaSystem(maps);
        }
        return count;
    }

    @Override
    @Transactional
    public int insertUser(UserAddDTO userAddDTO) {
        int count = userMapper.countUserByIdCard(userAddDTO.getIdCard());
        if (count == 0) {
            userAddDTO.setPassword(passwordService.encryptPassword(userAddDTO.getIdCard().substring(userAddDTO.getIdCard().length() - 6)));
            userAddDTO.setPasswordBackup(userAddDTO.getIdCard().substring(userAddDTO.getIdCard().length() - 6));
            userMapper.insertUser(userAddDTO);
        }
        return count;
    }

    @Override
    @Transactional
    public int userUpdate(UserUpdateDTO userUpdateDTO) {
        int count = 0;
        List<UserDetailVo> userDetailVos = userMapper.selectUserByIdCard(userUpdateDTO.getIdCard());
        if (userDetailVos.size() > 0) {
            if (userDetailVos.get(0).getId() == (userUpdateDTO.getId())) {
                userMapper.userUpdate(userUpdateDTO);
            }
        } else {
            count = userMapper.countUserByIdCard(userUpdateDTO.getIdCard());
            if (count == 0) {
                userUpdateDTO.setPassword(passwordService.encryptPassword(userUpdateDTO.getIdCard().substring(userUpdateDTO.getIdCard().length() - 6)));
                userUpdateDTO.setPasswordBackup(userUpdateDTO.getIdCard().substring(userUpdateDTO.getIdCard().length() - 6));
                userMapper.userUpdate(userUpdateDTO);
            }
        }
        return count;
    }

    @Override
    public UserListVo userLookDetail(Integer userId) {
        return userMapper.userLookDetail(userId);
    }

    @Override
    public void deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
    }

    @Override
    public void deleteUserAndIaSystem(Integer userId, Integer iasId) {
        userMapper.deleteUserAndIaSystem(userId, iasId);
    }

    @Override
    public List<RoleVo> selectRole() {
        return userMapper.selectRole();
    }

}
