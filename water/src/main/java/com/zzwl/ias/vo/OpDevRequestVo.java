package com.zzwl.ias.vo;

/**
 * Created by HuXin on 2018/1/15.
 */
public class OpDevRequestVo {
    int type;
    int[] args;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int[] getArgs() {
        return args;
    }

    public void setArgs(int[] args) {
        this.args = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            this.args[i] = args[i];
        }
    }
}
