package yoyoyousei.twitter.clone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import yoyoyousei.twitter.clone.domain.service.upload.StorageProperties;
import yoyoyousei.twitter.clone.domain.service.upload.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class TwitterCloneApplication {
	public static void main(String[] args) {
		SpringApplication.run(TwitterCloneApplication.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
	//		storageService.deleteAll();
			storageService.init();
		};
	}
}
