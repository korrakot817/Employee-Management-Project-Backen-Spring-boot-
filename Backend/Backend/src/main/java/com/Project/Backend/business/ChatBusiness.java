package com.Project.Backend.business;

import com.Project.Backend.exception.BaseException;
import com.Project.Backend.exception.ChatException;
import com.Project.Backend.model.MChatMessage;
import com.Project.Backend.model.MChatMessageRequest;
import com.Project.Backend.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatBusiness {

    private final SimpMessagingTemplate template; // มีไว้ใช้ส่งข้อความไปยังอีกที่ ผ่าน websocket

    public ChatBusiness(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void post(MChatMessageRequest request) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId(); //หาว่าใครกำลัง login อยู่
        if (opt.isEmpty()) {
            //throw error accessDined
            throw ChatException.accessDined();

        }

        final String destination = "/topic/chat";

        MChatMessage payload = new MChatMessage();
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());

        //covertAndSend ค้องการ 2 อย่าง 1. ส่งไปที่ไหน 2. ส่งอะไรไป
        template.convertAndSend(destination, payload);

    }
}
