package com.dexter.musify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.dexter.musify"])
class MusifyApplication

fun main(args: Array<String>) {
	runApplication<MusifyApplication>(*args)
}
