package hosung.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {"hosung.batch", "hosung.database"})
public class BatchApp implements ApplicationRunner {
    @Autowired
    List<Job> jobs;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job productImportJob;


    public static void main(String[] args) {
        SpringApplication.run(BatchApp.class, args);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("### Job beans ###");
        jobs.forEach(j -> System.out.println(" - " + j.getName()));

//        String file = args.getOptionValues("file").get(0);   // --file=…
//
//        JobParameters params = new JobParametersBuilder()
//                .addString("file", file)
//                .addLong("ts", System.currentTimeMillis())    // 중복 실행 방지
//                .toJobParameters();
//
//        jobLauncher.run(productImportJob, params);

    }
}