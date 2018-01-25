/*
 * Copyright 2018 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package org.quartz.examples.example16;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by naqichuan 2018/1/25 14:53
 */
public class TestJob implements Job {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Hello, this is a test job! - " + new Date());
    }

    public static void main(String[] args) throws Exception {
        SchedulerFactory schedulerfactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerfactory.getScheduler();

        JobKey jobKey = jobKey("test", "nqcx");
        JobDetail job = newJob(TestJob.class).withIdentity(jobKey).build();

        Date runTime = nextGivenSecondDate(null, 10);

        TriggerKey triggerKey = new TriggerKey("test", "nqcx");
        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                .withIdentity(triggerKey)
                .startAt(runTime)
                .withSchedule(simpleSchedule()
                        .withRepeatCount(10)
                        .withIntervalInSeconds(50))
                .build();

        scheduler.scheduleJob(job, Collections.singleton(trigger), true);

        scheduler.start();

        Thread.sleep(65L * 1000L * 60);
        scheduler.shutdown(true);
    }
}
