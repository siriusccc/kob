package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailsImpl;
import com.kob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;
    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        // 传入bot的id和更新信息
        int bot_id = Integer.parseInt(data.get("bot_id"));

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();
        if (title == null || title.length() == 0){
            map.put("error_message","标题不能为空");
            return map;
        }

        if (title.length() > 100){
            map.put("error_message","标题不能太长");
            return map;
        }

        if (description == null || description.length() == 0){
            description = "啥也没写";
            return map;
        }

        if (description.length() > 100){
            map.put("error_message","bot的描述不能大于100");
            return map;
        }

        if (content == null || content.length() == 0){
            map.put("error_message","代码不能为空");
            return map;
        }
        if (content.length() > 10000){
            map.put("error_message","代码不能太长");
            return map;
        }

        // 取bot需要涉及到对bot的查询，需要注入bot的mapper来读取bot信息
        Bot bot = botMapper.selectById(bot_id);

        if(bot == null){
            map.put("error_message", "bot不存在或已被删除");
            return map;
        }
        if(!bot.getUserId().equals(user.getId())){
            map.put("error_message", "没有权限修改");
            return map;
        }

        Bot new_bot = new Bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getCreatetime(),
                new Date()
        );

        botMapper.updateById(new_bot);
        
        map.put("error_message", "success");

        return map;
    }
}
