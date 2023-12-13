package com.kob.backend.service.impl.user.account;

import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailsImpl;
import com.kob.backend.service.user.account.UploadPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kob.backend.utils.UploadPic;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadPicServiceImpl implements UploadPicService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, String> uploadPic(String url) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();
//        String picName = pic.getOriginalFilename();

//        System.out.println(user.getId());
//        System.out.println(user.getUsername());
//        System.out.println(user.getPw());
//        System.out.println(user.getPhoto() + "->" + url);
//        System.out.println(user.getRating());

        User new_user = new User(
                user.getId(),
                user.getUsername(),
                user.getPw(),
                url,
                user.getRating()
        );

        userMapper.updateById(new_user);
        map.put("error_message", "success");
        return map;
    }
}
