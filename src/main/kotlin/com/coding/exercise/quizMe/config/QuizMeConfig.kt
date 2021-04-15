package com.coding.exercise.quizMe.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurerSupport
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
class QuizMeConfig : AsyncConfigurerSupport() {

    @Bean
    fun taskExecutor(): Executor? {
        return ThreadPoolTaskExecutor().apply {
            corePoolSize = 5
            maxPoolSize = 10
            setQueueCapacity(500)
            setThreadNamePrefix("QuizLookup-")
            initialize()
        }
    }
}