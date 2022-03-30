package com.dexter.musify.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import javax.validation.constraints.NotBlank

@Configuration
@ConfigurationProperties(prefix = "api")
class Constants {

    @NotBlank
    var musicBrainz: String = ""

    @NotBlank
    var wikidata: String = ""

    @NotBlank
    var wikipedia: String = ""

    @NotBlank
    var coverArt: String = ""

    companion object {
        const val WIKIDATA = "wikidata"
        const val MUSIC_BRAINZ_QUERY = "?&fmt=json&inc=url-rels+release-groups"
    }

}