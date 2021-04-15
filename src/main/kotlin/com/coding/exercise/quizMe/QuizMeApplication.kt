package com.coding.exercise.quizMe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync


@SpringBootApplication
@EnableAsync
class QuizMeApplication

fun main(args: Array<String>) {
	runApplication<QuizMeApplication>(*args)
}
