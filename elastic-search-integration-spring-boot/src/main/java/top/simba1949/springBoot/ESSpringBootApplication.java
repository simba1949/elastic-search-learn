package top.simba1949.springBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author anthony
 * @version 2023/9/16 14:07
 */
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "top.simba1949.springBoot.repository")
public class ESSpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(ESSpringBootApplication.class, args);
	}
}