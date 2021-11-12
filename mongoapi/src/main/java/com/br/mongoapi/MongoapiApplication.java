package com.br.mongoapi;

import com.br.mongoapi.model.Address;
import com.br.mongoapi.model.Gender;
import com.br.mongoapi.model.Student;
import com.br.mongoapi.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*
	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate template){
		return args -> {
			Address address = new Address("Brazil", "São Paulo", "02936030");
			Student student = new Student("Marco",
					"Grella",
					"marco.grella@gmail.com",
					Gender.MALE, address,
					List.of("Computer Science", "Maths"),
					BigDecimal.TEN,
					LocalDateTime.now());

			String email = "marco.grella@gmail.com";

			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(email));

			List<Student> students = template.find(query, Student.class);

			if(students.size() > 1){
				throw new IllegalStateException("Found many students with same " + email);
			}

			if(students.isEmpty()){
				System.out.println("Inserting student " + student);
				repository.insert(student);
			} else{
				System.out.println(student + " already exists");
			}

		};
	}

	 */

}
