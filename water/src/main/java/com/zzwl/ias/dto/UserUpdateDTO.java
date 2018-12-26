package com.zzwl.ias.dto;

import javax.validation.constraints.NotNull;

/**
 * 用户修改
 * Created by Lvpin on 2018/11/14.
 */
public class UserUpdateDTO {
    @NotNull(message = "用户id不能为空")
    private Integer id;
    @NotNull(message = "姓名不能为空")
    private String name;
    @NotNull(message = "电话不能为空")
    private String mobile;
    @NotNull(message = "身份证号不能为空")
    private String idCard;
    private Integer provinceId;
    private Integer cityId;
    private Integer districtId;
    private Integer townId;
    private Integer villageId;
    private String address;
    private String password;
    private String passwordBackup;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordBackup() {
        return passwordBackup;
    }

    public void setPasswordBackup(String passwordBackup) {
        this.passwordBackup = passwordBackup;
    }

    @NotNull
    public Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getMobile() {
        return mobile;
    }

    public void setMobile(@NotNull String mobile) {
        this.mobile = mobile;
    }

    @NotNull
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(@NotNull String idCard) {
        this.idCard = idCard;
    }

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
}
