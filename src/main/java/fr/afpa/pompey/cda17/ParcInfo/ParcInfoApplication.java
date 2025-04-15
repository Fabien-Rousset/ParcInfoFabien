package fr.afpa.pompey.cda17.ParcInfo;

import config.CustomProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@EnableConfigurationProperties(CustomProperties.class)
@SpringBootApplication
public class ParcInfoApplication implements CommandLineRunner {

	@Autowired
	private CustomProperties props;

	public static void main(String[] args) {
		SpringApplication.run(ParcInfoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(props.getApiUrl());
	}
}
