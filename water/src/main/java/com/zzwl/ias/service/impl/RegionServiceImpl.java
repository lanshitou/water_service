package com.zzwl.ias.service.impl;

import com.zzwl.ias.mapper.CommonRegionExtMapper;
import com.zzwl.ias.service.RegionService;
import com.zzwl.ias.vo.RegionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Lvpin on 2018/11/8.
 */
@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private CommonRegionExtMapper commonRegionExtMapper;

    /**
     * 获取region结构
     *
     * @param pid
     * @return
     */
    @Override
    public List<RegionVo> selectRegionTree(Integer pid) {
        List<RegionVo> regionVos = commonRegionExtMapper.selectRegionTree(pid);
        return regionVos;
    }

    /**
     * 添加新地址
     *
     * @param pid
     * @param name
     * @return
     */
    @Override
    @Transactional
    public int insertRegion(Integer pid, String name) {
        int count = commonRegionExtMapper.selectRegionCountByname(name);
        int id = 0;
        if (count == 0) {
            commonRegionExtMapper.insertRegion(pid, name);
        }
        id = commonRegionExtMapper.selectLastId();
        return id;
    }

    @Override
    public String selectNameById(Integer id) {
        return commonRegionExtMapper.selectNameById(id);
    }

    @Override
    public String getParentNameById(Integer id) {
        Map<String, Object> resultMap = commonRegionExtMapper.selectPidById(id);
        StringBuilder name = new StringBuilder((String) resultMap.get("name"));
        Integer pid = (Integer) resultMap.get("pid");
        if (pid != 0) {
            String parentNameById = getParentNameById(pid);
            name.insert(0, parentNameById);
        }
        return String.valueOf(name);
    }
}
