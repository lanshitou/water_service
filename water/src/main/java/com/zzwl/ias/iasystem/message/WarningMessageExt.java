package com.zzwl.ias.iasystem.message;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.MessageDO;
import com.zzwl.ias.domain.WarningDO;

import java.io.IOException;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-17
 * Time: 8:24
 */
public class WarningMessageExt {
    private Long warningId;

    public Long getWarningId() {
        return warningId;
    }

    public void setWarningId(Long warningId) {
        this.warningId = warningId;
    }

    public static WarningMessageExt getExtFromMessage(MessageDO messageDO){
        try {
            return AppContext.objectMapper
                    .readValue((String)messageDO.getExtension(), WarningMessageExt.class);
        } catch (IOException e) {
            return null;
        }
    }

}
