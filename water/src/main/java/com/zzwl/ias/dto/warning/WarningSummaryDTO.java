package com.zzwl.ias.dto.warning;

import java.util.Date;

/**
 * Description:
 * User: HuXin
 * Date: 2018-10-26
 * Time: 14:43
 */
public class WarningSummaryDTO {
    private Long id;
    private Date produceTime;
    private String location;
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(Date produceTime) {
        this.produceTime = produceTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
