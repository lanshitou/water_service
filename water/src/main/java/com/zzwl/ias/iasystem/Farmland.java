package com.zzwl.ias.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.FarmlandRecord;
import com.zzwl.ias.domain.IrrigationAreaRecord;
import com.zzwl.ias.dto.iasystem.FarmlandDTO;
import com.zzwl.ias.dto.iasystem.IrriAreaDTO;
import com.zzwl.ias.iasystem.constant.IaSystemConstant;
import com.zzwl.ias.vo.FarmlandBasicVo;
import com.zzwl.ias.vo.FarmlandVo;
import com.zzwl.ias.vo.IrrigationAreaVo;
import com.zzwl.ias.vo.iasystem.IrriAreaConfigVo;
import org.apache.shiro.SecurityUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HuXin on 2017/12/13.
 */
public class Farmland extends IasNormalObject {
    private FarmlandRecord farmlandRecord;
    private List<IrrigationArea> irrigationAreas = new ArrayList<>();  //灌溉区域

    public Farmland(FarmlandRecord farmlandRecord) {
        super();
        this.farmlandRecord = farmlandRecord;
        setAddr(IasObjectAddr.createIasFarmlandAddr(farmlandRecord.getIasystemId(), farmlandRecord.getId()));
    }

    @Override
    public int getId() {
        return farmlandRecord.getId();
    }

    @Override
    String getName() {
        return farmlandRecord.getName();
    }

    @Override
    public int getSortOrder() {
        return farmlandRecord.getSortOrder();
    }

    public FarmlandVo getFarmlandVo() {
        FarmlandVo farmlandVo = new FarmlandVo(farmlandRecord);
        farmlandVo.setIrrigationAreas(getIrrigationAreaVos());
        farmlandVo.setNormalDevices(getNormalDeviceVos());
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            farmlandVo.setDcPoints(getSensorPointVos());
        } else {
            farmlandVo.setDcPoints(getDcPointVos());
        }
        return farmlandVo;
    }

    public FarmlandVo getFarmlandAreaVo() {
        FarmlandVo farmlandVo = new FarmlandVo(farmlandRecord);
        farmlandVo.setIrrigationAreas(getIrriAreaVos());
        return farmlandVo;
    }

    public FarmlandBasicVo getFarmlandBasicVo() {
        FarmlandBasicVo farmlandVo = new FarmlandBasicVo(farmlandRecord);
        //todo
        farmlandVo.setValveStatus(AppContext.iaSystemService.getValveStatus(farmlandRecord.getIasystemId(), farmlandRecord.getId()));
        farmlandVo.setDcPointStatus(AppContext.iaSystemService.getDcPointStatus(farmlandRecord.getIasystemId(), farmlandRecord.getId()));
        return farmlandVo;
    }

    public ErrorCode updateFarmland(FarmlandRecord farmlandRecord) {
        this.farmlandRecord = farmlandRecord;
        return ErrorCode.OK;
    }

    public ErrorCode addIrrigationArea(IrrigationAreaRecord irrigationAreaRecord) {
        for (IrrigationArea irrigationArea : irrigationAreas) {
            if (irrigationArea.getId() == irrigationAreaRecord.getId()) {
                return ErrorCode.IRRIGATION_AREA_EXIST;
            }
        }
        IrrigationArea irrigationArea = new IrrigationArea(irrigationAreaRecord);
        irrigationAreas.add(irrigationArea);
        return ErrorCode.OK;
    }

    public ErrorCode delIrrigationArea(IrriAreaConfigVo irriAreaConfigVo) {
        for (IrrigationArea irrigationArea : irrigationAreas) {
            if (irrigationArea.getId() == irriAreaConfigVo.getId()) {
                irrigationAreas.remove(irrigationArea);
                return ErrorCode.OK;
            }
        }
        return ErrorCode.IRRIGATION_AREA_NOT_EXIST;
    }

    public ErrorCode updateIrrigation(IrriAreaConfigVo irriAreaConfigVo) {
        for (IrrigationArea irrigationArea : irrigationAreas) {
            if (irrigationArea.getId() == irriAreaConfigVo.getId()) {
                irrigationArea.setName(irriAreaConfigVo.getName());
                irrigationArea.setSortOrder(irriAreaConfigVo.getSortOrder());
                return ErrorCode.OK;
            }
        }
        return ErrorCode.IRRIGATION_AREA_NOT_EXIST;
    }

    public IrrigationArea getIrrigationArea(int irrigationAreaId) {
        for (IrrigationArea irrigationArea : irrigationAreas) {
            if (irrigationArea.getId() == irrigationAreaId) {
                return irrigationArea;
            }
        }

        return null;
    }

    private LinkedList<IrrigationAreaVo> getIrrigationAreaVos() {
        if (irrigationAreas.size() != 0) {
            LinkedList<IrrigationAreaVo> irrigationAreaVoList = new LinkedList<>();
            for (IrrigationArea irrigationArea : irrigationAreas) {
                irrigationAreaVoList.add(irrigationArea.getIrrigationAreaVo());
            }
            return irrigationAreaVoList;
        }
        return null;
    }

    private LinkedList<IrrigationAreaVo> getIrriAreaVos() {
        if (irrigationAreas.size() != 0) {
            LinkedList<IrrigationAreaVo> irrigationAreaVoList = new LinkedList<>();
            for (IrrigationArea irrigationArea : irrigationAreas) {
                irrigationAreaVoList.add(irrigationArea.getIrriAreaVo());
            }
            return irrigationAreaVoList;
        }
        return null;
    }

    public IasObject getDeviceById(long id) {
        IasObject iasObject;

        iasObject = getDevById(id);
        if (iasObject != null) {
            return iasObject;
        }

        for (IrrigationArea irrigationArea : irrigationAreas) {
            iasObject = irrigationArea.getDeviceById(id);
            if (iasObject != null) {
                return iasObject;
            }
        }
        return null;
    }

    public IasObject findIasObjectByAddr(IasObjectAddr addr) {
        IasObject iasObject;

        //检查是否为自身
        if (getAddr().equals(addr)){
            return this;
        }

        //检查是否为自身的可操作设备或者传感器
        iasObject = findByAddr(addr);
        if (iasObject != null) {
            return iasObject;
        }

        //在灌溉区中查找
        for (IrrigationArea irrigationArea : irrigationAreas) {
            iasObject = irrigationArea.findIasObjectByAddr(addr);
            if (iasObject != null) {
                return iasObject;
            }
        }
        return null;
    }


    //TODO 接口梳理

    /** 获取农田详细信息
     * @param type 查询类型
     * @return 农田详细信息
     */
    FarmlandDTO getFarmlandDetail(Integer type){
        FarmlandDTO farmlandDTO = new FarmlandDTO(farmlandRecord);
        if (type == IaSystemConstant.QUERY_TYPE_ALL) {
            farmlandDTO.setDcPoints(getDcPointVos());
            farmlandDTO.setDevices(getNormalDeviceVos());
        }
        if (irrigationAreas.size() != 0) {
            LinkedList<IrriAreaDTO> irriAreaDTOS = new LinkedList<>();
            for (IrrigationArea irrigationArea : irrigationAreas) {
                irriAreaDTOS.add(irrigationArea.getIrriAreaDetail(type));
            }
            farmlandDTO.setIrriAreas(irriAreaDTOS);
        }
        return farmlandDTO;
    }
}
