package com.chero.client.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by hxh on 2018/5/17.
 */
@Slf4j
public class ImageUtil {


    private ImageUtil() {}

    /**
     *
     * @param file      图像文件
     * @param filePath  图像本地路径根目录
     * @param cdnPath   CDN路径根目录
     * @param filePath  路径目录
     * @return
     * @throws IOException
     */
    public static CustomImagePath upload(MultipartFile file, String filePath, String cdnPath, String profile) throws IOException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
//        if (file.getSize() > 11000) {
//            log.error("【头像上传】文件过大，size={}", file.getSize());
//            throw new RuntimeException("头像图片太大,将图片控制在100*100以内");
//        }
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUIDUtil.getUUID() + suffix;
        // 完整的本地路径 + 图像名称
        String localFileName = filePath + profile + fileName;

        // 没有就创建文件夹
        File directory = new File(filePath + profile);
        if(!directory.exists()){
            directory.mkdirs();
        }
        File localFile = new File(localFileName);

        file.transferTo(localFile);
        log.info("【图片上传】,fileName={}，filePath={}", fileName, filePath + profile);

        // 完整的CDN路径 + 图像名称
        String cdnFileName = cdnPath + profile + fileName;

        CustomImagePath customImagePath = new CustomImagePath(profile + fileName, localFileName, cdnFileName);
        return customImagePath;
    }
}

