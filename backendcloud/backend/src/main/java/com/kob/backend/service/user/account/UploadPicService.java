package com.kob.backend.service.user.account;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadPicService {
    public Map<String, String> uploadPic(String url);
}
