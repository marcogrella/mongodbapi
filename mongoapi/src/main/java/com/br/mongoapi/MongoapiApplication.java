package com.br.mongoapi;

import com.br.mongoapi.model.Address;
import com.br.mongoapi.model.Gender;
import com.br.mongoapi.model.Student;
import com.br.mongoapi.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class MongoapiApplication {

	public static void main(String[] args) {

		SpringApplication.run(MongoapiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository){
		return args -> {
			Address address = new Address("Brazil", "São Paulo", "02936030");
			Student student = new Student("Marco",
					"Grella",
					"marco.grella@gmail.com",
					Gender.MALE, address,
					List.of("Computer Science", "Maths"),
					BigDecimal.TEN,
					LocalDateTime.now());
			repository.save(student);
		};
	}

}
