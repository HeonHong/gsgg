package com.gsgg.gsggbe.scheduler;

import com.gsgg.gsggbe.characters.service.CrtMainService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SchedulerJob {

    @Autowired
    private final CrtMainService crtMainService;

    //스케줄링 돌아가는 시간 설정, 서버 올라간 후 무조건 돌아가기 때문에 주석 처리
    @Scheduled(cron = "0 0 22 * * ?")   //매일 오후 10시에 실행
    public void setCrtInfoToDB() {
        crtMainService.setAPICharacterInfos();
    }

}
