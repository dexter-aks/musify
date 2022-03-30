package com.dexter.musify.controller

import com.dexter.musify.model.ArtistDto
import com.dexter.musify.service.IMusicService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/musify")
class MusicController(private val musicService: IMusicService) {

    @GetMapping(path = ["/music-artist/details/{mbid}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMusicArtistDetailsByMBID(@PathVariable mbid: String): ArtistDto {
        return musicService.fetchArtistDetails(mbid)
    }
}