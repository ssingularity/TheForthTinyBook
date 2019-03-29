package com.example.demo.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.SysUser;
import com.google.common.collect.Maps;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocket {
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<>();
    private String username;
    private Session session;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session){
//        SecurityContext securityContext= SecurityContextHolder.getContext();
//        Authentication authentication=securityContext.getAuthentication();
//        SysUser user=(SysUser) authentication.getPrincipal();
//        this.username = user.getUsername();
        this.username = username;
        this.session = session;
        clients.put(username, this);
        //TODO 提醒所有人谁上线了
    }

    @OnError
    public void onError(Session session, Throwable error){
        System.out.println(error.getMessage());
    }

    @OnClose
    public void onClose(){
        clients.remove(username);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        JSONObject jsonObject = JSON.parseObject(message);
        String textMessage = jsonObject.getString("message");
        Map<String, Object> map = Maps.newHashMap();
        map.put("message", textMessage);
        map.put("fromUserName", username);
        map.put("toUserName", "所有人");
        sendMessageAll(JSON.toJSONString(map));
    }

    private void sendMessageAll(String message) throws IOException{
        for (WebSocket item : clients.values()){
            item.session.getAsyncRemote().sendText(message);
        }
    }
}
