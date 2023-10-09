package com.laveberry.SpringBatchTutorial.job.ValidatedParam;

import com.laveberry.SpringBatchTutorial.job.ValidatedParam.Validator.FileParamValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * desc : 파일 이름 파라미터 전달 그리고 검증
 * run : --spring.batch.job.names=validatedParamJob -fileName=test.csv
 */
@Configuration
@RequiredArgsConstructor
public class ValidatedParamJobConfig {

    //빌더 팩토리 생성
    //final 선업해주지 않아서 Autowired 주입
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job validatedParamdJob(Step validatedParamdStep) {
        return jobBuilderFactory.get("validatedParamJob")
                .incrementer(new RunIdIncrementer())
//                .validator(new FileParamValidator()) //tasklet까지 가지 않고 job이 검증 수행 가능
                .validator(multipleValidator())
                .start(validatedParamdStep)
                .build();
    }

    //다수의 validator 등록
    private CompositeJobParametersValidator multipleValidator() {
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
        validator.setValidators(Arrays.asList(new FileParamValidator()));

        return validator;
    }

    @JobScope //job 하위
    @Bean //메소드 이름찾아 특정 빈 찾아서 주입
    public Step validatedParamdStep(Tasklet validatedParamTasklet) {
        return stepBuilderFactory.get("validatedParamStep")
                .tasklet(validatedParamTasklet) // tasklet:  읽고 쓸 필요가 없는 단순한 배치
                .build();
    }

    @StepScope //스탭 하위 실행
    @Bean
    public Tasklet validatedParamTasklet(@Value("#{jobParameters['fileName']}")String fileName) {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println(fileName);
                System.out.println("Validated Param Batch");
                return RepeatStatus.FINISHED;
            }
        };
    }

}
