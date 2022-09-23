package com.task.ably

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AblyApplication

fun main(args: Array<String>) {
    runApplication<AblyApplication>(*args)
}
