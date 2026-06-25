package in.georgepaultony.progress;

import in.georgepaultony.progress.storage.config.SupabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SupabaseProperties.class)
public class ProgressApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgressApiApplication.class, args);
	}

}
