package com.coding.exercise.quizMe.quiz.service

import com.coding.exercise.quizMe.quiz.model.QuestionAndAnswers
import com.coding.exercise.quizMe.quiz.model.QuizByCategory
import com.coding.exercise.quizMe.quiz.model.QuizResponse
import com.coding.exercise.quizMe.quiz.model.ThirdPartyQuiz
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletableFuture.completedFuture


@Service
class QuizService(restTemplateBuilder: RestTemplateBuilder) {

    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    @Async
    fun getQuiz(): CompletableFuture<QuizResponse>? {
        // Grab our quizzes from Third Party DB
        val filmQuiz = getThirdPartyQuizResponse(urlFilm)
        val musicQuiz = getThirdPartyQuizResponse(urlMusic)

        // Combine into our final object
        val result = QuizResponse((listOf(filmQuiz, musicQuiz)))

        return completedFuture(result)
    }

    fun getThirdPartyQuizResponse(url: String): QuizByCategory {
        val response: ResponseEntity<String> = restTemplate.getForEntity(url, String::class)

        // Deserialize the JSON we get from the response into our Third Party Response model
        val mapper = jacksonObjectMapper()
        val thirdPartyQuizNode: JsonNode = mapper.readTree(response.body).path("results")
        val reader = mapper.readerFor(object : TypeReference<List<ThirdPartyQuiz>>() {})
        val thirdPartyQuizes: List<ThirdPartyQuiz> = reader.readValue(thirdPartyQuizNode)

        // With our Third Party Response objects loaded, transform into list of our QuestionAndAnswers object
        val quizList: List<QuestionAndAnswers> = thirdPartyQuizes.map { quiz ->

            val allAnswers = quiz.incorrect_answers.toMutableList()
            allAnswers.add(quiz.correct_answer)

            QuestionAndAnswers(
                quiz.type,
                quiz.difficulty,
                quiz.question,
                allAnswers,
                quiz.correct_answer
            )
        }

        return QuizByCategory(thirdPartyQuizes[0].category, quizList)
    }

    companion object {
        const val urlFilm  = "https://opentdb.com/api.php?amount=5&category=11"
        const val urlMusic = "https://opentdb.com/api.php?amount=5&category=12"
    }
}