package br.com.postech.netflixo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Properties;


@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "br.com.postech.netflixo.domain.repository")
public class App {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(App.class);
		Properties prop = new Properties();
		prop.setProperty("spring.servlet.multipart.location", System.getProperty("java.io.tmpdir"));
		app.setDefaultProperties(prop);
		app.run(args);
	}

}
