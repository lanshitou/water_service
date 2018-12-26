package com.zzwl.ias.iasystem.permission;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-24
 * Time: 9:45
 */
public class UserIasPermissionSet {
    private LinkedList<Integer> adminPermissionIas;
    private LinkedList<Integer> retrievePermissionIas;
    private LinkedList<Integer> farmlands;

    public LinkedList<Integer> getAdminPermissionIas() {
        return adminPermissionIas;
    }

    public void setAdminPermissionIas(LinkedList<Integer> adminPermissionIas) {
        this.adminPermissionIas = adminPermissionIas;
    }

    public LinkedList<Integer> getRetrievePermissionIas() {
        return retrievePermissionIas;
    }

    public void setRetrievePermissionIas(LinkedList<Integer> retrievePermissionIas) {
        this.retrievePermissionIas = retrievePermissionIas;
    }

    public LinkedList<Integer> getFarmlands() {
        return farmlands;
    }

    public void setFarmlands(LinkedList<Integer> farmlands) {
        this.farmlands = farmlands;
    }
}
