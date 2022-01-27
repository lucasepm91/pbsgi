package br.edu.infnet.sgi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories(basePackages = "br.edu.infnet.sgi.repositories")
@EnableTransactionManagement
@EntityScan(basePackages = "br.edu.infnet.sgi.models")
public class PbsgiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PbsgiApplication.class, args);
	}

}
