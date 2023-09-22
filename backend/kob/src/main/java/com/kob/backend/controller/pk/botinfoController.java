package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pk/")
public class botinfoController {
    @RequestMapping("bot/")
    public String bot(){
        return "hello bot";
    }
}
