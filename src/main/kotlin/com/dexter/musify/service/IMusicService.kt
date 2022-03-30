package com.dexter.musify.service

import com.dexter.musify.model.ArtistDto

interface IMusicService {

    fun fetchArtistDetails(mbid: String): ArtistDto
}