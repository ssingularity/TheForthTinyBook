package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebSocketController {
    @RequestMapping("/websockets")
    public String webSocket(@RequestParam(name = "username") String username, Model model){
        model.addAttribute("username", username);
        return "websocket.html";
    }
}
