package hosung.batch.product.config;

import hosung.batch.product.dto.ProductCsvRow;
import hosung.batch.product.io.ProductItemWriter;
import hosung.database.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ProductJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager tx;

    @Bean
    public Step productImportStep(
            FlatFileItemReader<ProductCsvRow> productCsvReader,
            ItemProcessor<ProductCsvRow, Product> productProcessor,
            ProductItemWriter productItemWriter) {


        return new StepBuilder("productImportStep", jobRepository)
                .<ProductCsvRow, Product>chunk(100, tx)
                .reader(productCsvReader)
                .processor(productProcessor)
                .writer(productItemWriter) //customWriter
                .build();
    }

    @Bean
    public Job productImportJob(Step productImportStep) {   // Job 이름도 product 로 교체
        return new JobBuilder("productImportJob", jobRepository)
                .start(productImportStep)
                .incrementer(new org.springframework.batch.core.launch.support.RunIdIncrementer()) // 중복 실행 방지
                .build();
    }
}