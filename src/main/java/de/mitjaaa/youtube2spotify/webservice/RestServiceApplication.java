package de.mitjaaa.youtube2spotify.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {

	public static void startService() {
		SpringApplication.run(RestServiceApplication.class);
	}
}
