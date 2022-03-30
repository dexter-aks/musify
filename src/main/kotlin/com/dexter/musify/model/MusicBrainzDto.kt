package com.dexter.musify.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MusicBrainzDto(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("gender")
    val gender: String?,

    @JsonProperty("country")
    val country: String,

    @JsonProperty("disambiguation")
    val disambiguation: String,

    @JsonProperty("release-groups")
    val releaseGroups: List<ReleaseGroup>,

    @JsonProperty("relations")
    val relations: List<Relation>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReleaseGroup(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("title")
    val title: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Relation(

    @JsonProperty("type")
    val type: String,

    @JsonProperty("url")
    val url: UrlDto
)

data class UrlDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("resource")
    val resource: String
)
