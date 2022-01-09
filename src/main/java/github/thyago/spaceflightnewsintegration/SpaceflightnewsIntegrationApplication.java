package github.thyago.spaceflightnewsintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpaceflightnewsIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceflightnewsIntegrationApplication.class, args);
    }

}
