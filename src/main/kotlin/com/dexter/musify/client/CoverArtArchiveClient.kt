package com.dexter.musify.client

import com.dexter.musify.configuration.Constants
import com.dexter.musify.exception.RetryAPIException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.ConnectException
import java.util.logging.Logger

@Service
class CoverArtArchiveClient(private val restTemplate: RestTemplate,
                            private val constants: Constants) {

    private val logger: Logger = Logger.getLogger(CoverArtArchiveClient::class.simpleName)

    fun getCoverArt(imageId: String): String {
        var imageUrl = ""
        if(imageId.isEmpty()) return imageUrl
        try {
            val url = buildUrl(imageId)
            val response: ResponseEntity<String> = restTemplate.getForEntity(url, String::class.java)
            when (response.statusCodeValue) {
                307 -> when {
                    response.headers.location != null -> imageUrl = response.headers.location.toString()
                }
            }
        } catch (ex: ConnectException) {
            logger.info("CoverArtArchiveClient Retry Exception Happened: ${ex.localizedMessage}")
            throw RetryAPIException("Retry CoverArtArchiveClient")
        } catch (ex: Exception) {
            logger.info("CoverArtArchiveClient Exception Happened: ${ex.localizedMessage}")
        }
        return imageUrl
    }

    private fun buildUrl(imageId: String) = "${constants.coverArt}$imageId/front"
}