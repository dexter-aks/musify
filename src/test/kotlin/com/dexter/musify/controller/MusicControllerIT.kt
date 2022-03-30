package com.dexter.musify.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class MusicControllerIT(@Autowired private val mockMvc: MockMvc) {

    @Test
    fun successfully_get_artist_details_for_valid_mbid_mj() {
        val mbid = "f27ec8db-af05-4f36-916e-3d57f91ecf5e"
        val name = "Michael Jackson"
        val gender = "Male"
        mockMvc.perform(
            MockMvcRequestBuilders.get("/musify/music-artist/details/$mbid"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("mbid").value(mbid))
            .andExpect(jsonPath("name").value(name))
            .andExpect(jsonPath("gender").value(gender))
    }

    @Test
    fun successfully_get_artist_details_for_valid_mbid_nirvana() {
        val mbid = "5b11f4ce-a62d-471e-81fc-a69a8278c7da"
        val name = "Nirvana"
        val country = "US"
        mockMvc.perform(
            MockMvcRequestBuilders.get("/musify/music-artist/details/$mbid"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("mbid").value(mbid))
            .andExpect(jsonPath("name").value(name))
            .andExpect(jsonPath("country").value(country))
    }
}