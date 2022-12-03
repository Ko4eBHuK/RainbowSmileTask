package okladnikov.bool.rainbowsmiletask.repository

import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okladnikov.bool.rainbowsmiletask.network.BasicAuthInterceptor
import okladnikov.bool.rainbowsmiletask.network.DocumentApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DocumentsRepository {
    private val documentWebApi: DocumentApi
    private const val BASE_URL = "http://api-test.tdera.ru/api/"
    private const val user = "l12345678"
    private const val password = "p12345678"

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(BasicAuthInterceptor(user, password))
            .build()

        documentWebApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(DocumentApi::class.java)
    }

    fun getDocuments() = flow {
        emit(documentWebApi.getDocumentList().data.mapNotNull { it })
    }
}