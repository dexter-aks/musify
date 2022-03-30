package com.dexter.musify.model

data class ArtistDto(
    val mbid: String,
    val name: String,
    val gender: String,
    val country: String,
    val disambiguation: String,
    val description: String,
    val albums: List<Album>
) {
    data class Album(
        val id: String,
        val title: String,
        val imageUrl: String
    )
}