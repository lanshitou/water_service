package com.zzwl.ias.common;

import org.apache.shiro.SecurityUtils;

public class CurrentUserUtil {
    public static Integer getCurrentUserId() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal == null){
            return 0;
        }
        return Integer.parseInt(principal.toString());
    }
}
