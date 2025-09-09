package org.example.tutorial.configuration;

import org.example.tutorial.scheduler.ShowData;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail showDataDetail() {
        return JobBuilder.newJob(ShowData.class)
                .withIdentity("showDataJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger emailJobTrigger(JobDetail emailJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(emailJobDetail)
                .withIdentity("showDataTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                .build();
    }

}
