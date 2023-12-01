package xxjavax.validator.javaxvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class JavaxValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaxValidatorApplication.class, args);
	}

	@Bean
	public MethodValidationPostProcessor validationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

}
