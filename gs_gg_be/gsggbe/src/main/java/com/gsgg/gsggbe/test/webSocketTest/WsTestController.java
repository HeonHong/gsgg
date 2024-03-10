package com.gsgg.gsggbe.test.webSocketTest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsTestController {

    @MessageMapping("/receive")
    @SendTo("/send")
    public WsTestDTO SocketHandler(WsTestDTO wsTestDTO){

        String userName=wsTestDTO.getUserName();
        String content=wsTestDTO.getContent();

        WsTestDTO result = new WsTestDTO(userName,content);
        return result;
    }

    @MessageMapping("/sendLiveCheck")
    @SendTo("/liveCheck")
    public int SocketHandler(int liveNum){
        System.out.println(liveNum);
        liveNum++;


        return liveNum;
    }
}
