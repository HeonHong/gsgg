package com.gsgg.gsggbe.batchJob;

import lombok.AllArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@EnableScheduling
public class RunJob {
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final BatchJobConfig batchJobConfig;

    @Autowired
    private Scheduler scheduler;

    @Bean
    public boolean startScheduler() {
        try {
            scheduler.start();
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public RunJob(JobLauncher jobLauncher, JobExplorer jobExplorer, BatchJobConfig batchJobConfig) {
        this.jobLauncher = jobLauncher;
        this.jobExplorer = jobExplorer;
        this.batchJobConfig = batchJobConfig;
    }

    @Scheduled(fixedRate = 5000)
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
                    .addDate("date", new Date())
                    .toJobParameters();
            jobLauncher.run(batchJobConfig.myFirstJob(), jobParameters);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
