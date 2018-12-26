package com.zzwl.ias.iasystem.irrigation.task;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Description:
 * User: HuXin
 * Date: 2018-06-29
 * Time: 9:20
 */
public enum NormalIrriTaskResult {

    OK(0, "成功"),
    OPEN_VALVES_FAIL(1, "开启阀门失败"),
    OPEN_PUMP_FAIL(2, "开启水泵失败"),
    CLOSE_PUMP_FAIL(3, "关闭水泵失败"),
    CLOSE_VALVES_FAIL(4, "关闭阀门失败"),
    USER_CANCEL_OK(100, "用户取消"),
    USER_CANCEL_OPEN_VALVES_FAIL(101, "用户取消但是开启阀门失败"),
    USER_CANCEL_OPEN_PUMP_FAIL(102, "用户取消但是开启水泵失败"),
    USER_CANCEL_CLOSE_PUMP_FAIL(103, "用户取消但是关闭水泵失败"),
    USER_CANCEL_CLOSE_VALVES_FAIL(104, "用户取消但是关闭阀门失败");


    private int code;
    private String msg;

    NormalIrriTaskResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static NormalIrriTaskResult getByValue(int value) {
        for (NormalIrriTaskResult result : NormalIrriTaskResult.values()) {
            if (result.code == value) {
                return result;
            }
        }

        throw new IllegalArgumentException(String.format("No error definition has code of %d", value));
    }


    @Override
    public String toString() {
        return String.format("Result code is %d, msg is %s", code, msg);
    }
}
