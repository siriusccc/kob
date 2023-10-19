package com.kob.backend.controller.pk;

import javafx.fxml.FXMLLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pk/")
public class BotinfoController {
    @RequestMapping("bot/")
    public Map<String, String> botRaing(){
        Map<String, String> bot1 = new HashMap<String, String>();
        bot1.put("name", "zz");
        return bot1;
    }
}
