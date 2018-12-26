package com.zzwl.ias.service.impl;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DcPointOwnerType;
import com.zzwl.ias.domain.DcPointRecord;
import com.zzwl.ias.domain.IrrigationAreaRecord;
import com.zzwl.ias.domain.NormalDeviceRecord;
import com.zzwl.ias.iasystem.IaSystem;
import com.zzwl.ias.mapper.DcPointRecordExtMapper;
import com.zzwl.ias.mapper.IrrigationAreaRecordMapper;
import com.zzwl.ias.mapper.NormalDeviceRecordExtMapper;
import com.zzwl.ias.service.FarmlandService;
import com.zzwl.ias.vo.iasystem.FarmlandConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by HuXin on 2017/12/13.
 */
@Service
public class FarmlandServiceImpl implements FarmlandService {
    @Autowired
    IrrigationAreaRecordMapper irrigationAreaRecordMapper;
    @Autowired
    NormalDeviceRecordExtMapper normalDeviceRecordExtMapper;
    @Autowired
    DcPointRecordExtMapper dcPointRecordExtMapper;

    @Override
    public List<IrrigationAreaRecord> getIrrigationAreaRecordByFarmland(int farmlandId) {
        return irrigationAreaRecordMapper.selectByFarmlandId(farmlandId);
    }

    @Override
    public List<DcPointRecord> getDcPointRecordByFarmLand(int farmlandId) {
        return dcPointRecordExtMapper.getDcPointRecordByOwner(farmlandId, DcPointOwnerType.FARMLAND);
    }

    @Override
    public List<NormalDeviceRecord> getNormalDeviceRecordByFarmland(int farmlandId) {
        return normalDeviceRecordExtMapper.selectByFarmlandId(farmlandId);
    }

    @Override
    @Transactional
    public ErrorCode addFarmland(FarmlandConfigVo farmlandConfigVo) {
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(farmlandConfigVo.getIaSystemId());
        if (iaSystem == null) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        return iaSystem.addFarmland(farmlandConfigVo);
    }
}
