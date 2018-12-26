package com.zzwl.ias.vo;

public class DcPointDataVo {
    int dataType;
    long startDate;
    long endDate;
    Object[] collections;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public Object[] getCollections() {
        return collections;
    }

    public void setCollections(Object[] collections) {
        this.collections = collections;
    }
}
