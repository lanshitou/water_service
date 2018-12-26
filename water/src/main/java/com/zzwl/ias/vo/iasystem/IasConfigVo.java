package com.zzwl.ias.vo.iasystem;

/**
 * Created by HuXin on 2018/2/26.
 */
public class IasConfigVo {
    private Integer id;
    private Integer type;
    private String name;
    private String alias;
    private Integer mode;
    private Integer maxIrrNum;
    private Integer sortOrder;
    private Integer provinceId;
    private Integer cityId;
    private Integer districtId;
    private Integer townId;
    private Integer villageId;
    private String address;

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean check() {
        if (type == null || name == null || mode == null || maxIrrNum == null || sortOrder == null) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getMaxIrrNum() {
        return maxIrrNum;
    }

    public void setMaxIrrNum(Integer maxIrrNum) {
        this.maxIrrNum = maxIrrNum;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
