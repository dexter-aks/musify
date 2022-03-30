package com.dexter.musify.converter

import com.dexter.musify.model.ArtistDto
import com.dexter.musify.model.ArtistDto.Album
import com.dexter.musify.model.MusicBrainzDto
import org.springframework.stereotype.Service

@Service
class ArtistDtoConverter {

    fun convert(artistInfo: MusicBrainzDto, artistDescription: String, albums: List<Album>): ArtistDto {
        return ArtistDto(
            mbid = artistInfo.id,
            name = artistInfo.name,
            gender = artistInfo.gender.orEmpty(),
            country = artistInfo.country,
            disambiguation = artistInfo.disambiguation,
            description = artistDescription,
            albums = albums
        )
    }
}