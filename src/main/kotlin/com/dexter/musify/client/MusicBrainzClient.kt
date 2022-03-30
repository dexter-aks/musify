package com.dexter.musify.client

import com.dexter.musify.configuration.Constants
import com.dexter.musify.configuration.Constants.Companion.MUSIC_BRAINZ_QUERY
import com.dexter.musify.exception.MusicBrainzClientException
import com.dexter.musify.exception.RetryAPIException
import com.dexter.musify.model.MusicBrainzDto
import org.springframework.http.ResponseEntity
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.ConnectException
import java.util.logging.Logger

@Service
class MusicBrainzClient(private val restTemplate: RestTemplate,
                        private val constants: Constants) {

    private val logger: Logger = Logger.getLogger(MusicBrainzClient::class.simpleName)

    @Retryable(value = [RetryAPIException::class])
    fun getMusicDetailsById(mbid: String): MusicBrainzDto {
        try {
            val url = buildUrl(mbid)
            val response: ResponseEntity<MusicBrainzDto> = restTemplate.getForEntity(url, MusicBrainzDto::class.java)
            if (response.statusCodeValue == 200)
                return response.body!!
        } catch (ex: ConnectException) {
            logger.info("Retry Exception Happened: ${ex.localizedMessage}")
            throw RetryAPIException("Retry Music Brainz Client")
        } catch (ex: Exception) {
            logger.info("Exception Happened: ${ex.localizedMessage}")
        }
        throw MusicBrainzClientException("Unable to fetch artist details from music brainz api for mbid: $mbid")
    }

    private fun buildUrl(mbid: String) = "${constants.musicBrainz}$mbid$MUSIC_BRAINZ_QUERY"
}