package com.example.marvelcharapp.data.character.network

import com.example.marvelcharapp.data.base.APIError
import com.example.marvelcharapp.data.base.NetworkExceptionsMapper
import com.example.marvelcharapp.data.character.model.CatalogResponse
import com.example.marvelcharapp.domain.base.Failure
import com.example.marvelcharapp.domain.base.OperationResult
import com.example.marvelcharapp.domain.base.Success
import retrofit2.Call
import java.lang.Exception

class CharacterNetworkDatasource(
    private val service: CharacterService,
    private val networkExceptionsMapper: NetworkExceptionsMapper) {
    fun getCharacterList(offset: Int): OperationResult<CatalogResponse, APIError> {
        val call: Call<CatalogResponse> = service.getCatalogResponse(offset)
        return try {
            val response = call.execute()
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Failure(networkExceptionsMapper.mapNetworkException(
                        response.code(),
                        errorBody))
            }
        } catch (e: Exception) {
            Failure(networkExceptionsMapper.mapException(e))
        }
    }

    fun getCharacter(id: String): OperationResult<CatalogResponse, APIError> {
        val call: Call<CatalogResponse> = service.getDetailResponse(id)
        return try {
            val response = call.execute()
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                Failure(networkExceptionsMapper.mapNetworkException(
                    response.code(),
                    errorBody))
            }
        } catch (e: Exception) {
            Failure(networkExceptionsMapper.mapException(e))
        }
    }
}