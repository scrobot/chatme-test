package me.chat.test.api;

import me.chat.test.api.services.validation.ValidationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

	private ValidationService validationService;

	public GatewayApplication(ValidationService validationService) {
		this.validationService = validationService;
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@PostConstruct
	public void listenRedis() {
		validationService.startValidationProcess();
	}

}
