package com.dexter.musify.client

import com.dexter.musify.configuration.Constants
import com.dexter.musify.exception.RetryAPIException
import com.dexter.musify.model.WikipediaDto
import org.springframework.http.ResponseEntity
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.ConnectException
import java.util.logging.Logger

@Service
class WikipediaClient(private val restTemplate: RestTemplate,
                      private val constants: Constants) {

    private val logger: Logger = Logger.getLogger(WikipediaClient::class.simpleName)

    @Retryable(value = [RetryAPIException::class])
    fun getWikipediaData(title: String): String {

        var description = ""
        if (title.isEmpty()) return description
        try {
            val url = buildUrl(title)

            val response: ResponseEntity<WikipediaDto> = restTemplate.getForEntity(url, WikipediaDto::class.java)

            if (response.statusCodeValue == 200)
                description = response.body?.description.orEmpty()

        } catch (ex: ConnectException) {
            logger.info("WikipediaClient Retry Exception Happened: ${ex.localizedMessage}")
            throw RetryAPIException("Retry WikipediaClient")
        } catch (ex: Exception) {
            logger.info("WikipediaClient Exception Happened: ${ex.localizedMessage}")
        }
        return description
    }

    private fun buildUrl(title: String): String {
        val formattedTitle = title.replace(" ", "_")
        return "${constants.wikipedia}$formattedTitle"
    }
}