package com.example.marvelcharapp.data.character.network

import com.example.marvelcharapp.data.character.model.CatalogResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("characters?ts=1&apikey=79795d874a65e1d50d495d4d95667f8f&hash=21a135ebb8f877d130c0b8ab7109e48f")
    suspend fun getCatalogResponse(@Query("offset") offset: Int)
            : CatalogResponse

    @GET("characters/{id}?ts=1&apikey=79795d874a65e1d50d495d4d95667f8f&hash=21a135ebb8f877d130c0b8ab7109e48f")
    fun getDetailResponse(@Path("id") id: String)
            : Call<CatalogResponse>
}