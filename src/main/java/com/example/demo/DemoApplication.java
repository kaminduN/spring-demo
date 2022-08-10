package com.example.demo;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.utils.EnvConfigReader;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", EnvConfigReader.getConfig("service.port", "8080")));
        app.run(args);
	}

	@RequestMapping("/")
	public String home() {
		return "Hello Docker World";
	}

}
