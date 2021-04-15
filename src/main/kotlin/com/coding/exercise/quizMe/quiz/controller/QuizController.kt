package com.coding.exercise.quizMe.quiz.controller

import com.coding.exercise.quizMe.quiz.model.QuizResponse
import com.coding.exercise.quizMe.quiz.service.QuizService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
class QuizController {

    @Autowired
    val quizService: QuizService? = null


    @GetMapping("/coding/exercise/quiz")
    @Async
    fun getQuiz(): CompletableFuture<QuizResponse>? {

        return quizService?.getQuiz()
    }
}