package com.zzwl.ias.service.impl;

import com.aliyun.oss.OSSClient;
import com.zzwl.ias.common.Constants;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.ErrorCodeException;
import com.zzwl.ias.common.PropertiesUtil;
import com.zzwl.ias.configuration.OSSConfig;
import com.zzwl.ias.domain.Image;
import com.zzwl.ias.mapper.ImageExtMapper;
import com.zzwl.ias.service.FileService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lvpin on 2018/12/11.
 */
@Service
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    private ImageExtMapper imageExtMapper;
    @Autowired
    private OSSConfig ossConfig;

    @Override
    public Map uploadImage(MultipartFile file, Integer type) throws ErrorCodeException {

        if (null == file || file.isEmpty()) {
            return null;
        }
        if (file.getSize() > Constants.IMAGE_MAX_SIZE) {
            throw new ErrorCodeException(ErrorCode.FILE_IMAGE_SIZE_ERROR);
        }
        String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(Constants.DOT));
        Date date = new Date();
        String uploadFileName = date.getTime() + suffixName;
        if (StringUtils.isBlank(uploadFileName)) {
            throw new ErrorCodeException(ErrorCode.FILE_RENAME_ERR);
        }
        Image image;
        HashMap<String, Image> images = new HashMap<>();
        SimpleDateFormat adf = new SimpleDateFormat("yyyy/MM/dd/");
        String filePath = Constants.FILE_PATH_IMAGE + adf.format(new Date());
        String key = filePath + uploadFileName;
        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        try {
            image = new Image();
            image.setName(uploadFileName);
            image.setUrl(PropertiesUtil.getProperty("ftp.server.http.prefix") + key);
            //检查文件夹是否存在
            boolean found = ossClient.doesObjectExist(ossConfig.getBucketName(), filePath);
            if (!found) {
                ossClient.putObject(ossConfig.getBucketName(), filePath, new ByteArrayInputStream(new byte[0]));
            }
            ossClient.putObject(ossConfig.getBucketName(), key, file.getInputStream());
            imageExtMapper.insertImage(image);
            images.put("image", image);
            if (type == 1) { //上传缩略图
                InputStream inputStream = file.getInputStream();

                // 把图片读入到内存中
                BufferedImage bufImg = ImageIO.read(inputStream);
                // 压缩代码
                // 存储图片文件byte数组
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                //防止图片变红
                BufferedImage newBufferedImage = new BufferedImage(217, 190, BufferedImage.TYPE_INT_RGB);
                newBufferedImage.createGraphics().drawImage(bufImg, 0, 0, 217, 190, Color.WHITE, null);
                //先转成jpg格式来压缩,然后在通过OSS来修改成源 文件本来的后缀格式
                ImageIO.write(newBufferedImage, "jpg", bos);
                //获取输出流
                inputStream = new ByteArrayInputStream(bos.toByteArray());
                uploadFileName = date.getTime() + "_tag" + suffixName;
                filePath = Constants.FILE_PATH_IMAGE + adf.format(new Date());
                key = filePath + uploadFileName;
                ossClient.putObject(ossConfig.getBucketName(), key, inputStream);
                Image imageTag = new Image();
                imageTag.setName(uploadFileName);
                imageTag.setUrl(PropertiesUtil.getProperty("ftp.server.http.prefix") + key);
                imageExtMapper.insertImage(imageTag);
                images.put("imageTag", imageTag);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ErrorCodeException(ErrorCode.UPLOAD_FAILURE);
        } finally {
            ossClient.shutdown();
        }
        return images;
    }

}
