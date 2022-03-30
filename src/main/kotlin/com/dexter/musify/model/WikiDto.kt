package com.dexter.musify.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WikiDto(
    @JsonProperty("entities")
    val entities: Map<String, Entity>
)

data class Entity(
    @JsonProperty("sitelinks")
    val sitelinks: Sitelinks
)

data class Sitelinks(
    @JsonProperty("enwiki")
    val enwiki: Enwiki
)

data class Enwiki(
    @JsonProperty("title")
    val title: String
    )
