package com.zzwl.ias.controller.admin.iasystem;

import com.zzwl.ias.common.ErrorCodeException;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by Lvpin on 2018/12/11.
 */
@Controller
@RequestMapping("api/admin/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/image_upload/{type}")
    @ResponseBody
    public Object imageUpload(@RequestParam(value = "upload_file", required = false) MultipartFile file, @PathVariable Integer type) {
        Map images;
        try {
            images = fileService.uploadImage(file, type);
        } catch (ErrorCodeException e) {
            return Result.error(e.getErrorCode());
        }
        return Result.ok(images);
    }
}
