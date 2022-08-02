package com.example.reactiveproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.tools.agent.ReactorDebugAgent

@SpringBootApplication
class ReactiveProjectApplication

fun main(args: Array<String>) {
	ReactorDebugAgent.init()
	runApplication<ReactiveProjectApplication>(*args)
}
