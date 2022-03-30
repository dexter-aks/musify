package com.dexter.musify.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.retry.annotation.EnableRetry
import org.springframework.web.client.RestTemplate
import java.net.HttpURLConnection


@Configuration
@EnableRetry
class AppConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate(object : SimpleClientHttpRequestFactory() {
            override fun prepareConnection(connection: HttpURLConnection, httpMethod: String) {
                connection.instanceFollowRedirects = false
            }
        })
        return restTemplate
    }
}