package com.dexter.musify.service

import com.dexter.musify.client.CoverArtArchiveClient
import com.dexter.musify.client.MusicBrainzClient
import com.dexter.musify.client.WikiDataClient
import com.dexter.musify.client.WikipediaClient
import com.dexter.musify.configuration.Constants.Companion.WIKIDATA
import com.dexter.musify.converter.ArtistDtoConverter
import com.dexter.musify.model.ArtistDto
import com.dexter.musify.model.ArtistDto.Album
import com.dexter.musify.model.Relation
import com.dexter.musify.model.ReleaseGroup
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class MusicService(
    private val musicBrainzClient: MusicBrainzClient,
    private val wikiDataClient: WikiDataClient,
    private val wikipediaClient: WikipediaClient,
    private val coverArtArchiveClient: CoverArtArchiveClient,
    private val artistDtoConverter: ArtistDtoConverter
) : IMusicService {

    private val logger: Logger = Logger.getLogger(MusicService::class.simpleName)

    override fun fetchArtistDetails(mbid: String): ArtistDto {
        logger.info("Fetching Artist Details form mbid: $mbid")

        val artistInfo = musicBrainzClient.getMusicDetailsById(mbid)
        val wikiId = fetchWikiId(artistInfo.relations)
        val title = wikiDataClient.getWikiData(wikiId)
        val artistDescription = wikipediaClient.getWikipediaData(title)
        val albums = buildAlbums(artistInfo.releaseGroups)

        return artistDtoConverter.convert(artistInfo, artistDescription, albums)
    }

    private fun fetchWikiId(relations: List<Relation>): String {
        var wikiId = ""
        relations.forEach { relation ->
            when (relation.type) {
                WIKIDATA -> {
                    val wikiDataUrl = relation.url.resource
                    val wikiIds = wikiDataUrl.split("/")
                    wikiId = wikiIds[wikiIds.lastIndex]
                }
            }
        }
        return wikiId
    }

    private fun buildAlbums(releaseGroups: List<ReleaseGroup>): List<Album> {
        val albums = ArrayList<Album>()
        releaseGroups.forEach { release ->
            val id = release.id
            val title = release.title
            val imageUrl = coverArtArchiveClient.getCoverArt(id)
            val album = Album(id, title, imageUrl)
            albums.add(album)
        }
        return albums
    }
}