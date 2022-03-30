package com.dexter.musify.client

import com.dexter.musify.configuration.Constants
import com.dexter.musify.exception.RetryAPIException
import com.dexter.musify.model.WikiDto
import org.springframework.http.ResponseEntity
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.ConnectException
import java.util.logging.Logger

@Service
class WikiDataClient(private val restTemplate: RestTemplate,
                     private val constants: Constants) {

    private val logger: Logger = Logger.getLogger(WikiDataClient::class.simpleName)

    @Retryable(value = [RetryAPIException::class])
    fun getWikiData(wikiId: String): String {
        var title = ""
        if(wikiId.isEmpty()) return title
        try {
            val url = buildUrl(wikiId)
            val wikiDtoResponse: ResponseEntity<WikiDto> = restTemplate.getForEntity(url, WikiDto::class.java)
            if (wikiDtoResponse.statusCodeValue == 200)
                title = wikiDtoResponse.body?.entities?.get(wikiId)?.sitelinks?.enwiki?.title.orEmpty()

        } catch (ex: ConnectException) {
            logger.info("WikiDataClient Retry Exception Happened: ${ex.localizedMessage}")
            throw RetryAPIException("Retry WikiDataClient")
        } catch (ex: Exception) {
            logger.info("WikiDataClient Exception Happened: ${ex.localizedMessage}")
        }
        return title
    }

    private fun buildUrl(wikiId: String) = "${constants.wikidata}$wikiId.json"
}