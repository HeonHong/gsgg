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


    @Scheduled(fixedRate = 50000)
    public void setCrtInfoToDB() {
        crtMainService.selectCrtInfos();
    }

}
