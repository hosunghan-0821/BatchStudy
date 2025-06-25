package hosung.batch.product.config;

import hosung.batch.product.dto.ProductCsvRow;
import hosung.database.entity.Product;
import hosung.database.entity.ProductSize;
import hosung.database.entity.ProductSkuToken;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Configuration
public class ProductIoConfig {

    /* ========= ItemReader ========= */
    @Bean
    @StepScope
    public FlatFileItemReader<ProductCsvRow> productCsvReader(
            @Value("#{jobParameters['file']}") Resource csvFile) {

        return new FlatFileItemReaderBuilder<ProductCsvRow>()
                .name("productCsvReader")
                .resource(csvFile)
                .encoding(StandardCharsets.UTF_8.name())
                .linesToSkip(1)               // ── 첫 줄(헤더) 스킵
                .delimited()
                .delimiter(",")              // ── 탭 구분자
                .names(
                        "num",            // 0  NUM
                        "dbId",           // 1  DB_ID
                        "boutique",       // 2  BOUTIQUE
                        "brand",          // 3  BRAND
                        "sku",            // 4  SKU
                        "price",          // 5  PRICE
                        "count",          // 6  COUNT
                        "productSizes",   // 7  PRODUCT_SIZES
                        "productTokens"   // 8  PRODUCT_TOKENS
                )
                .fieldSetMapper(fs -> {       // ── DTO 매핑
                    ProductCsvRow row = new ProductCsvRow();
                    row.setBoutique(fs.readString("boutique"));
                    row.setBrand(fs.readString("brand"));
                    row.setSku(fs.readString("sku"));
                    row.setPrice(fs.readDouble("price"));
                    row.setCount(fs.readLong("count"));
                    row.setProductSizes(fs.readString("productSizes"));
                    row.setProductTokens(fs.readString("productTokens"));
                    return row;
                })
                .build();
    }

    /* ========= ItemProcessor ========= */
    @Bean
    public ItemProcessor<ProductCsvRow, Product> productProcessor() {
        return csv -> Product.builder()
                .boutique(csv.getBoutique())
                .brand(csv.getBrand())
                .sku(csv.getSku())
                .productSize(Arrays.stream(csv.getProductSizes().split(",")).map(v -> ProductSize.builder().name(v).build()).toList())
                .price(csv.getPrice())
                .count(csv.getCount())
                .productToken(Arrays.stream(csv.getProductTokens().split(",")).map(v -> ProductSkuToken.builder().token(v).build()).toList())
                .build();
    }

}