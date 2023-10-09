package com.laveberry.SpringBatchTutorial.job.FileDataReadWrite;

import com.laveberry.SpringBatchTutorial.job.FileDataReadWrite.dto.Player;
import com.laveberry.SpringBatchTutorial.job.FileDataReadWrite.dto.PlayerFieldSetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

/**
 * desc : 파일 읽고 쓰기
 * run : --spring.batch.job.names=fileReadWriteJob
 */
@Configuration
@RequiredArgsConstructor
public class FileDataReadWriteConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job fileReadWriteJob(Step fileReadWriteStep) {
        return jobBuilderFactory.get("fileReadWriteJob")
                .incrementer(new RunIdIncrementer())
                .start(fileReadWriteStep)
                .build();
    }

    @JobScope
    @Bean
    public Step fileReadWriteStep(ItemReader playerItemReader) {
        System.out.println("여기 들어옴???");
        return stepBuilderFactory.get("fileReadWriteStep")
                .<Player, Player>chunk(5)
                .reader(playerItemReader)
                .writer(new ItemWriter() {
                    @Override
                    public void write(List items) throws Exception {
                        //출력
                        items.forEach(System.out::println);
                    }
                })
                .build();
    }

    // FlatFileItemReader : reporitory 아닌 file 에서 읽어올수 있는
    @StepScope
    @Bean
    public FlatFileItemReader<Player> playerItemReader() {
        return new FlatFileItemReaderBuilder<Player>()
                .name("playerItemReader")
                .resource(new FileSystemResource("Players.csv"))//파일 담기
                .lineTokenizer(new DelimitedLineTokenizer())//데이터 나누는 기준(,)
                .fieldSetMapper(new PlayerFieldSetMapper())//읽어온 데이터 객체로 변경
                .linesToSkip(1) //첫번째줄은 제목으로 스킵
                .build();
    }
}
