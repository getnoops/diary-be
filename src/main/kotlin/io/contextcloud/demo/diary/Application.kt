package io.contextcloud.demo.diary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(DbConfigProperties::class)
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}

@ConfigurationProperties(prefix="db")
data class DbConfigProperties(
	val url: String,
	val username: String,
	val password: String,
	val driver: String,
	val poolSize: Int,
)
