package com.kob.backend.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class UploadPic {
    public static final String ali_domain = "https://jeff-pic.oss-cn-beijing.aliyuncs.com/";

    public static String uploadPic(MultipartFile file) throws IOException {
        // 生成文件名
        String originalFilename = file.getOriginalFilename();
        String ext = "." + FilenameUtils.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = uuid + ext;
        // 地域节点
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tFoKjjj7seCLtdV8BAZ";
        String accessKeySecert = "WbChmtcR23qDIaxLVJPYcNLKxprx83";
        // OSS客户端对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecert);
        ossClient.putObject(
                "jeff-pic",  // 仓库名
                fileName,       // 文件名
                file.getInputStream()
        );
        ossClient.shutdown();
        return ali_domain + fileName;
    }
}
