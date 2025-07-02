package com.cooksmart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CookSmartBackendApplication

fun main(args: Array<String>) {
	runApplication<CookSmartBackendApplication>(*args)
}
