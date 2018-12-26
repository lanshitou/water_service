package com.zzwl.ias.iasystem.message;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.MessageDO;

import java.io.IOException;

/**
 * Description:
 * User: HuXin
 * Date: 2018-10-09
 * Time: 15:15
 */
public class NotifyMessageExt {
    private Integer articleId;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public static NotifyMessageExt getExtFromMessage(MessageDO messageDO){
        try {
            return AppContext.objectMapper
                    .readValue((String)messageDO.getExtension(), NotifyMessageExt.class);
        } catch (IOException e) {
            return null;
        }
    }
}
