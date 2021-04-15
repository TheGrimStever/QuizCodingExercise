package com.coding.exercise.quizMe.quiz.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class ThirdPartyQuiz (
    @JsonProperty("category") val category: String,
    @JsonProperty("type") val type: String,
    @JsonProperty("difficulty") val difficulty: String,
    @JsonProperty("question") val question: String,
    @JsonProperty("correct_answer") val correct_answer: String,
    @JsonProperty("incorrect_answers") val incorrect_answers: List<String>
    )

data class QuizResponse (val quiz: List<QuizByCategory>)

data class QuizByCategory (val category: String,
                           val results: List<QuestionAndAnswers> )

data class QuestionAndAnswers(
    val type: String,
    val difficulty: String,
    val question: String,
    val all_answers: List<String>,
    val correct_answer: String
    )