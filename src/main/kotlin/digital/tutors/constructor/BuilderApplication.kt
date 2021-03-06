package digital.tutors.constructor

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import springfox.documentation.swagger2.annotations.EnableSwagger2
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = ["digital.tutors.*"])
@EnableSwagger2
class ConstructorApplication {

	@Bean
	fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
		return BCryptPasswordEncoder()
	}
}

fun main(args: Array<String>) {
	SpringApplication.run(ConstructorApplication::class.java, *args)
}
