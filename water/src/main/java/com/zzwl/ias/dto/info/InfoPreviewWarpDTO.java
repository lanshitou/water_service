package com.zzwl.ias.dto.info;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InfoPreviewWarpDTO {
    private String warpTitle;
    private InfoWarpTypeEnum warpType;
    private List<InfoPreviewDTO> content;

    public InfoPreviewWarpDTO(){}

    public InfoPreviewWarpDTO(String warpTitle, InfoWarpTypeEnum warpType, List<InfoPreviewDTO> content) {
        this.warpTitle = warpTitle;
        this.warpType = warpType;
        this.content = content;
    }

    public String getWarpTitle() {
        return warpTitle;
    }

    public void setWarpTitle(String warpTitle) {
        this.warpTitle = warpTitle;
    }

    public InfoWarpTypeEnum getWarpType() {
        return warpType;
    }

    public void setWarpType(InfoWarpTypeEnum warpType) {
        this.warpType = warpType;
    }

    public List<InfoPreviewDTO> getContent() {
        return content;
    }

    public void setContent(List<InfoPreviewDTO> content) {
        this.content = content;
    }
}