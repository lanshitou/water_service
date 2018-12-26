package com.zzwl.ias.service;

import com.zzwl.ias.common.ErrorCodeException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {

    /**
     * 上传图片
     *
     * @param file
     * @return 服务器上的图片路径
     */
    Map uploadImage(MultipartFile file, Integer type) throws ErrorCodeException;
}
