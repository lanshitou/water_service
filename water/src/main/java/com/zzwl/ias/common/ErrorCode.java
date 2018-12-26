package com.zzwl.ias.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by HuXin on 2017/12/11.
 */
public enum ErrorCode {
    OK(0, "OK"),
    ERROR(1, "ERROR"),

    /**HTTP错误码取值范围1-999*/

    /**
     * 通用错误码取值范围1000-9999
     */
    USER_NOT_LOGIN(1000, "用户未登录"),
    USER_ALREADY_LOGIN(1001, "用户已登录"),
    USER_AUTHC_FAIL(1002, "用户名或密码错误"),
    USER_EXIST(1003, "用户已存在"),
    INVALID_MOBILE(1100, "手机号码无效"),
    INVALID_PASSWORD(1101, "密码格式不正确,只允许6-20位的数字和字母组合"),
    INVALID_VCODE(1102, "验证码无效"),
    USER_NOT_EXIST(1103, "用户不存在"),
    MOBILE_EXIST(1104, "该手机号码已经被注册"),
    INVALID_OLD_PASSWORD(1105, "原始密码不正确"),
    INVALID_USERNAME(1106, "昵称格式不对"),
    SEND_MSG_FAIL(1200, "发送失败"),
    VCODE_ALREADY_SENT(1201, "验证码已经发送"),
    SEND_EVENT_FAIL(1300, "Send event fail"),
    LOCK_INTERRUPTED(1400, "Waiting lock is interrupted"),
    DATABASE_ERROR(1500, "操作数据库失败"),
    AUTHORIZATION_CHECK_FAIL(1600, "Authorization check fail"),
    INTERNAL_SERVER_ERROR(1700, "未知错误"),
    APP_NEED_UPDATE(1800, "App need update"),
    NOT_DATA(1900, "没有数据"),

    EMPTY(2000, "无结果"),

    /**
     * 智能灌溉相关错误码取值范围10000-19999
     */
    //命令执行结果错误码取值范围10001-10255
    CMD_REPLACED(10005, "Command is replaced by a new one"),
    //通用错误码取值范围10300-10999
    INVALID_PARAMS(10300, "参数非法"),
    CMD_ERROR_TYPE_NOT_DEF(10301, "Command reply error type not defined"),
    SET_GLOBAL_CONFIG_FAIL(10302, "设置全局配置失败"),
    USER_HAS_NO_PERMISSION(10303, "用户没有操作权限"),
    //设备及连接管理相关错误码取值范围11000-11999
    MASTER_CONTROLLER_EXIST(11000, "首部控制器已存在"),
    MASTER_CONTROLLER_NOT_EXIST(11001, "首部控制器不存在"),
    CONTROLLER_EXIST(11002, "控制器已经存在"),
    CONTROLLER_NOT_EXIST(11003, "控制器不存在"),
    DEVICE_EXIST(11004, "Device already exist"),
    DEVICE_NOT_EXIST(11005, "设备不存在"),
    SYSTEM_OFFLINE(11006, "MasterController is offline"),
    CONTROLLER_OFFLINE(11007, "Controller is offline"),
    DEVICE_OFFLINE(11008, "Device is offline"),
    DEV_ADDR_INVALID(11009, "设备地址无效"),
    DEV_TYPE_INVALID(11010, "Device type invalid"),
    OPERATION_IN_PROGRESS(11100, "Operation is in progress"),
    OPERATION_INTERRUPTED(11101, "Operation is interrupted by a new one"),
    OPERATION_INVALID(11102, "Operation is not supported or args invalid"),
    OPERATION_TIMEOUT(11103, "Operation timeout"),
    OPERATION_ERR_UNKNOWN(11104, "Operation error unknown"),
    OPERATION_ERR_REPLY_INVALID(11105, "Operation reply data invalid"),
    IASYSTEM_IN_AUTO_MODE(11106, "智慧农业系统处于自动运行模式"),
    CONNECTION_CLOSED(11200, "连接已关闭"),
    DEV_SYSTEM_IN_AUTO_MODE(12205, "IaSystem in auto mode"),
    DEV_HAS_SUB_DEVICE(12206, "要删除的设备下还存在其它设备"),
    //农田管理相关错误码12000-12199
    FARMLAND_NOT_EXIST(12000, "农田不存在"),
    FARMLAND_EXIST(12001, "农田已经存在"),
    //灌溉任务相关错误码取值范围12200-12399
    IRRIGATION_AREA_NOT_EXIST(12200, "灌溉区域不存在"),
    IRRIGATION_AREA_ID_REPEATED(12201, "Irrigation area id repeated"),
    IRRIGATION_TASK_EXIST(12202, "灌溉任务已经存在"),
    IRRIGATION_NO_WAITING_TASK(12203, "No waiting irrigation taskmng"),
    IRRIGATION_TASK_NOT_EXIST(12204, "灌溉任务不存在"),
    IRRIGATION_SYSTEM_IN_MANUAL_MODE(12205, "IaSystem in manual mode"),
    UPDATE_IRRIGATION_TASK_FAIL(12206, "更新灌溉任务失败"),
    IRRIGATION_IN_PROGRESS(12207, "存在正在执行的灌溉任务"),
    IRRI_TASK_LACKOF_VALVE_OR_PUMP(12208, "没有水泵或者阀门,不能浇水"),
    IRRI_TASK_R_OK(12300, "灌溉任务执行成功"),
    IRRI_TASK_R_UER_CANCEL_OK(12301, "用户取消灌溉任务，执行"),

    //智慧农业系统管理相关错误码12400-12599
    IASYSTEM_EXIST(12400, "智慧农业系统已经存在"),
    IASYSTEM_NOT_EXIST(12401, "智慧农业系统不存在"),
    DC_POINT_EXIST(12402, "Data collection point exist"),
    IRRIGATION_AREA_EXIST(12403, "灌溉区已经存在"),
    NORMAL_DEVICE_EXIST(12404, "Normal device exist"),
    USER_IASYSTEM_NOT_EXIST(12405, "用户没有智慧农业系统"),
    DEVICE_ALREADY_BOUND(12406, "Device already bound to a iasystem"),
    DEVICE_NOT_SENSOR(12407, "Device is not a sensor"),
    DEVICE_NOT_PUMP(12408, "Device is not a pump"),
    IRRIGATION_TASK_RUNNING(12409, "Exist running irrigation task"),
    PUMP_ALREADY_EXIST(12410, "水泵已经存在"),
    DEVICE_NOT_VALVE(12411, "Device is not a valve"),

    ADD_OR_RM_DC_POINT_UNSUPPORTED(12412, "不支持添加或者删除数据采集点"),
    IRRI_FER_SYSTEM_EXIST(12413, "水肥一体化系统已经存在"),
    IAS_DEVICE_EXIST(12414, "设备已经存在"),
    NOT_OP_DEVICE(12415, "不是可操作设备"),
    DEV_NOT_VALVE(12426, "设备不是阀门"),
    DEVICE_USAGE_TYPE_INVALID(12427, "设备使用类型错误"),
    NOT_SENSOR(12428, "不是传感器"),
    IRRI_FER_SYSTEM_NOT_EXIST(12429, "水肥一体化系统不存在"),
    DEV_NOT_PUMP(12430, "设备不是水泵"),
    IAS_DEVICE_NOT_EXIST(12431, "设备不存在"),

    IAS_NOT_DELETABLE(12432, "智慧农业系统中存在其它对象，无法删除"),
    CONTROLLER_HAVE_DEVICE(12450, "控制器上存在设备,无法删除"),
    CTROLLER_IS_USING_IN_IASYSTEM(12451, "控制器正在农业系统中使用"),
    DEVICE_IS_USING_IN_IASYSTEM(12452, "设备正在农业系统中使用"),
    FARMLAND_NOT_DELETABLE(12453, "大棚中存在其它对象，无法删除"),
    IRRIFER_NOT_DELETABLE(12455, "水肥一体化中存在其他对象,无法删除"),
    IRRIFER_JUST_ONE(12456, "该系统中已经存在一个水肥一体化系统"),
    DEV_EXISTIN_IAS(12457, "该设备已经添加到了智慧农业系统中,请勿重复添加"),
    IRRIFER_ONLY_PUMP(12458, "水肥一体化只能添加水泵"),
    SENSOR_ON_DEV(12459, "设备上接有传感器,无法删除"),

    REGISTER_CAMERA_TO_YS_FAIL(12460, "注册摄像头失败"),
    ADD_CAMERA_FAIL(12461, "添加摄像头失败"),
    CAMERA_NOT_EXIST(12462, "摄像头不存在"),
    UNREGISTER_CAMERA_FROM_YS_FAIL(12463, "取消摄像头注册失败"),
    REMOVE_CAMERA_FAIL(12464, "删除摄像头失败"),
    IASYSTEM_HAS_NOT_CAMERA(12465, "没有摄像头"),
    ACCESS_TOKEN_UNAVAILABLE(12466, "无法获取AccessToken"),

    MESSAGE_NOT_EXIST(12501, "消息不存在"),

    WARNING_NOT_EXIST(10601, "告警不存在"),
    WARNING_NOT_SUPPORT_MANUAL_CLEAR(10602, "告警不支持手动清除"),

    ARTICLE_NOT_EXIST(10701, "文章不存在"),

    ADDRESS_ALREADY_EXISTS(17000, "地址已存在"),
    SYSTEM_ALREADY_BOUND(16000, "该系统已绑定"),
    FARMLAND_ALREADY_BOUND(16010, "该农田已绑定"),
    //APP自身内部错误码取值范围18000-19999
    /**
     * 智慧新农相关错误码取值范围20000-29999
     */
    //文章相关错误码
    NO_SUBGROUPS(15000, "没有子组"),
    EXIST_ARTICLE_CAT(15001, "存在文章或子组"),
    ARTICLE_IS_ADD(15002, "该文章已添加"),
    //通知相关错误
    NOT_USER(15003, "该地区没有用户"),

    /* 上传相关错误码1701 - 1703 */
    UPLOAD_FAILURE(1701, "上传失败"),
    UPLOAD_SUCCESS(1702, "上传成功"),
    FILE_ZIP_ERR(1703, "文件压缩失败"),
    FILE_RENAME_ERR(1704, "图片命名失败"),
    FILE_IMAGE_SIZE_ERROR(1705, "图片不能大于2MB");

    public static int ERROR_IAS_START_VALUE = 10000;

    private int value;
    private String descTemplate;

    ErrorCode(int value, String descTemplate) {
        this.value = value;
        this.descTemplate = descTemplate;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    public String getDescTemplate() {
        return this.descTemplate;
    }

    public static ErrorCode getByValue(int value) {
        for (ErrorCode code : ErrorCode.values()) {
            if (code.value == value) {
                return code;
            }
        }

        throw new IllegalArgumentException(String.format("No error definition has value of %d", value));
    }

    public static ErrorCode getByCommandResult(int value) {
        if (value == 0) {
            return ErrorCode.OK;
        } else {
            return getByValue(value + ERROR_IAS_START_VALUE);
        }
    }

    @Override
    public String toString() {
        return String.format("Error code is %d, info is %s", value, descTemplate);
    }

}