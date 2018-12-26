package com.zzwl.ias.service;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DcPointRecord;
import com.zzwl.ias.domain.IrrigationAreaRecord;
import com.zzwl.ias.domain.NormalDeviceRecord;
import com.zzwl.ias.vo.iasystem.FarmlandConfigVo;

import java.util.List;

/**
 * Created by HuXin on 2017/12/13.
 */
public interface FarmlandService {
    List<IrrigationAreaRecord> getIrrigationAreaRecordByFarmland(int farmlandId);

    List<DcPointRecord> getDcPointRecordByFarmLand(int farmlandId);

    List<NormalDeviceRecord> getNormalDeviceRecordByFarmland(int farmlandId);

    ErrorCode addFarmland(FarmlandConfigVo farmlandConfigVo);
}
