package okladnikov.bool.rainbowsmiletask.network

import okladnikov.bool.rainbowsmiletask.model.DocumentResponse
import retrofit2.http.GET

interface DocumentApi {
    @GET("getdocumentlist")
    suspend fun getDocumentList(): DocumentResponse
}