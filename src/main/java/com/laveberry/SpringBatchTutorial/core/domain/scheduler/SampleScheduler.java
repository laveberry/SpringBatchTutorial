package com.laveberry.SpringBatchTutorial.core.domain.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class SampleScheduler {
    @Autowired
    private Job helloWorldJob;
    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(cron = "* */1 * * * *") //초 분 시간.... -> 1분에 한번씩
    public void helloworldJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );

        //다른 파라미터 값을 넣어줌으로써 실행. 파라미터 없으면 똑같은 job으로 인식해 실행안됨
        jobLauncher.run(helloWorldJob, jobParameters);
    }

}
