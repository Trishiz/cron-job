package zw.co.nbs.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Import({ScheduleConfig.class,BusinessImplConfig.class})
public class CronJobApplication {
    public static void main(String[] args) {SpringApplication.run(CronJobApplication.class, args);

    }
}

