package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.UploadPicService;
import com.kob.backend.utils.UploadPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class UploadPicController {
    @Autowired
    private UploadPicService uploadPicService;

    @PostMapping("/api/user/account/uploadpic/")
    public Map<String, String> uploadPic(@RequestParam MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        System.out.println("上传的图片名：" + filename);

        String picUrl = UploadPic.uploadPic(file);

        System.out.println("地址为:" + picUrl);
        return uploadPicService.uploadPic(picUrl);
    }

//    public Map<String, String> uploadPic(@RequestParam Map<String, String> map) {
//        String filename = map.get("filename");
//        System.out.println("上传的图片名：" + filename);
//        return uploadPicService.uploadPic(filename);
//    }
}
