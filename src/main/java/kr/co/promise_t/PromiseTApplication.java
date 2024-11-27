package kr.co.promise_t;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PromiseTApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromiseTApplication.class, args);
    }
}
