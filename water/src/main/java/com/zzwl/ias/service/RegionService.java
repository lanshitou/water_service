package com.zzwl.ias.service;

import com.zzwl.ias.vo.RegionVo;

import java.util.List;

/**
 * Created by Lvpin on 2018/11/8.
 */
public interface RegionService {
    /**
     * 获取region结构
     *
     * @param pid
     * @return
     */
    List<RegionVo> selectRegionTree(Integer pid);

    /**
     * 添加新地址
     */
    int insertRegion(Integer pid, String name);

    /**
     * 根据id查找name
     */
    String selectNameById(Integer id);

    String getParentNameById(Integer id);
}
