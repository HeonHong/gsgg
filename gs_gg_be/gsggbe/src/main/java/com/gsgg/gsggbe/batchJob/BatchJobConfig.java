package com.gsgg.gsggbe.batchJob;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import java.util.Date;

@Slf4j
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchJobConfig {

    private final JobLauncher jobLauncher;
    private final Job myFirstJob;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final JobExplorer jobExplorer;

    public BatchJobConfig(JobLauncher jobLauncher, Job myFirstJob, JobRepository jobRepository, PlatformTransactionManager transactionManager, JobExplorer jobExplorer) {
        this.jobLauncher = jobLauncher;
        this.myFirstJob = myFirstJob;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.jobExplorer = jobExplorer;
    }

    @Scheduled(fixedRate = 180000)
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
                    .addDate("date", new Date())
                    .toJobParameters();
            jobLauncher.run(myFirstJob, jobParameters);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Bean
    public Job myFirstJob(){
        return new JobBuilder("myFirstJob", jobRepository)
                .start(myFirstStep())
                .build();
    }

    @Bean
    public Step myFirstStep(){
        return new StepBuilder("myFirstStep",jobRepository)
                .<String, String>chunk(1000,transactionManager)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader<String> itemReader(){
        return new ItemReader<String>() {
            @Override
            public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                return "Read OK";
            }
        };
    }

    @Bean
    public ItemWriter<String> itemWriter(){
        return strList -> {
            strList.forEach(
                    str -> System.out.println("str =============" + str)
            );
        };
    }
}
