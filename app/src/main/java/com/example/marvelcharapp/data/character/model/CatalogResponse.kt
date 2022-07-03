package com.example.marvelcharapp.data.character.model

import com.google.gson.annotations.SerializedName


data class CatalogResponse(
    val data: DataResponse?
)

data class DataResponse (
    val results : List<CharacterResponse>?
)

data class CharacterResponse(
    val id: Int?,
    val name: String?,
    val description: String?,
    @SerializedName("thumbnail")
    val image: MarvelImageUrlResponse?
)

data class MarvelImageUrlResponse(
    val path: String?,
    val extension: String?
        )