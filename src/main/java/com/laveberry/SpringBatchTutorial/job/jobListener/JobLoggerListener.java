package com.laveberry.SpringBatchTutorial.job.jobListener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

// Job 실행 전, 후 로그 추가를 위한 리스너
@Slf4j
public class JobLoggerListener implements JobExecutionListener {
    private static String BEFORE_MESSAGE = "{} Job is Running";
    private static String AFTER_MESSAGE = "{} Job is Done. (Status: {})";

    //job 실행 전
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(BEFORE_MESSAGE, jobExecution.getJobInstance().getJobName());
    }

    //job 실행 후
    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(AFTER_MESSAGE,
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus());

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            // email
            log.info("Job is Failed");
        }
    }
}
