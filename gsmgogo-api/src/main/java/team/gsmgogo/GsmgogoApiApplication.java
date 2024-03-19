package team.gsmgogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GsmgogoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsmgogoApiApplication.class, args);
    }

}