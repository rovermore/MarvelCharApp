package com.example.marvelcharapp.presentation.main.model

data class CharacterUIModel(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val image: MarvelImageUrl = MarvelImageUrl()
)

class MarvelImageUrl(
    val path: String = "",
    val extension: String = ""
) {
    fun getImageUrl(): String {
        return path.replace("http", "https").plus("/standard_xlarge").plus(".").plus(extension)
    }

    fun getBigImageUrl(): String {
        return path.replace("http", "https").plus("/standard_fantastic").plus(".").plus(extension)
    }

}