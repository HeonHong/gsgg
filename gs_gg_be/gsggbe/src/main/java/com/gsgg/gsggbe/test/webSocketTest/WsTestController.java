package com.gsgg.gsggbe.test.webSocketTest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WsTestController {

    @Autowired
    private final WsLiveNumDTO wsLiveNumDTO;
    private int number=0;



    @MessageMapping("/receive")
    @SendTo("/send")
    public WsTestDTO SocketMessageHandler(WsTestDTO wsTestDTO){

        String userName=wsTestDTO.getUserName();
        String content=wsTestDTO.getContent();

        WsTestDTO result = new WsTestDTO(userName,content);
        return result;
    }

    @MessageMapping("/liveCheck")
    @SendTo("/live")
    public int SocketLiveCheckHandler(int isExit){
        System.out.println(isExit);
        if(isExit==0){

            ++number;
        }else{
            --number;
        }


        return number;
    }

}
