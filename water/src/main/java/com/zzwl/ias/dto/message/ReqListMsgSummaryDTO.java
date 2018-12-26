package com.zzwl.ias.dto.message;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.dto.RequestDTO;
import com.zzwl.ias.iasystem.constant.MessageConstant;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-20
 * Time: 14:34
 */
public class ReqListMsgSummaryDTO extends RequestDTO {
    private Integer iasId;
    private Integer category;
    private Integer offset;
    private Integer limit;

    public ReqListMsgSummaryDTO(Integer iasId, Integer category, Integer offset, Integer limit) {
        this.iasId = iasId;
        this.category = category;
        this.offset = offset;
        this.limit = limit;
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public void check() {
        AssertEx.isTrue(
                category != null && offset != null && limit != null,
                ErrorCode.INVALID_PARAMS);

        if (category.equals(MessageConstant.MSG_CAT_NOTIFICATION)) {
            iasId = null;
        } else {
            if (iasId == null) {
                AssertEx.isOK(ErrorCode.INVALID_PARAMS);
            }
        }
    }
}
