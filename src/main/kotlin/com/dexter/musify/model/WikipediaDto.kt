package com.dexter.musify.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WikipediaDto(
    @JsonProperty("extract_html")
    val description: String = ""
)
