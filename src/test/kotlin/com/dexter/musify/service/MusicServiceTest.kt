package com.dexter.musify.service

import com.dexter.musify.client.CoverArtArchiveClient
import com.dexter.musify.client.MusicBrainzClient
import com.dexter.musify.client.WikiDataClient
import com.dexter.musify.client.WikipediaClient
import com.dexter.musify.converter.ArtistDtoConverter
import com.dexter.musify.model.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
internal class MusicServiceTest(@Mock private val artistDtoConverter: ArtistDtoConverter,
                                @Mock private val musicBrainzClient: MusicBrainzClient,
                                @Mock private val wikiDataClient: WikiDataClient,
                                @Mock private val wikipediaClient: WikipediaClient,
                                @Mock private val coverArtArchiveClient: CoverArtArchiveClient
) {

    @InjectMocks
    private lateinit var musicService:MusicService

    private val mbid = "34357067-8f7f-4c7a-8d5e-99b6e60f7891"
    private val name = "Michael Jackson"
    private val gender = "Male"
    private val country = "US"
    private val disambiguation = "King of Pop"
    private val wikiId = "Q2831"
    private val description = "American Singer"
    private val imageId = "97e0014d-a267-33a0-a868-bb4e2552918a"
    private val imageUrl = "https://archive.org/download/mbid-7d65853b-d547-4885-86a6-51df4005768c/mbid-7d65853b-d547-4885-86a6-51df4005768c-1619682960.jpg"

    @Test
    fun successfully_fetchArtistDetails_for_given_mbid() {
        `when`(musicBrainzClient.getMusicDetailsById(anyString())).thenReturn(musicBrainzDto)
        `when`(wikiDataClient.getWikiData(anyString())).thenReturn(name)
        `when`(wikipediaClient.getWikipediaData(anyString())).thenReturn(description)
        `when`(coverArtArchiveClient.getCoverArt(anyString())).thenReturn(imageUrl)
        `when`(artistDtoConverter.convert(musicBrainzDto, description, buildAlbums)).thenReturn(artistDto)

        val response = musicService.fetchArtistDetails(mbid)
        assertEquals(response.mbid, mbid)
        assertEquals(response.name, name)
        assertEquals(response.gender, gender)
        assertEquals(response.disambiguation, disambiguation)

        verify(musicBrainzClient).getMusicDetailsById(mbid)
        verify(wikiDataClient).getWikiData(wikiId)
        verify(wikipediaClient).getWikipediaData(name)
        verify(coverArtArchiveClient).getCoverArt(imageId)
    }

    private val artistDto = ArtistDto(
        mbid = mbid,
        name = name,
        gender = gender,
        country = country,
        disambiguation = disambiguation,
        description = description,
        albums = buildAlbums
    )

    private val musicBrainzDto = MusicBrainzDto(
        id = mbid,
        name = name,
        gender = gender,
        country = country,
        disambiguation = disambiguation,
        releaseGroups = listOf(ReleaseGroup(id= "97e0014d-a267-33a0-a868-bb4e2552918a", title = "Got to Be There")),
        relations = listOf(Relation(type = "wikidata", url = UrlDto(id = "27011c1f-bd4f-474c-ae04-23e00f79ccd9",
            resource = "https://www.wikidata.org/wiki/Q2831")))
    )

    private val buildAlbums: List<ArtistDto.Album>
        get() {
            val album1 = ArtistDto.Album(
                id = "97e0014d-a267-33a0-a868-bb4e2552918a",
                title = "Got to Be There",
                imageUrl = imageUrl
            )
            val album2 = ArtistDto.Album(
                id = "51343255-0ad3-3635-9aa2-548ba939b23e",
                title = "Ben",
                imageUrl = imageUrl
            )
            return listOf(album1, album2)
        }
}