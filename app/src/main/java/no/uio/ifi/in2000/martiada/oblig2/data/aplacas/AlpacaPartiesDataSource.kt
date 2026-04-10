package no.uio.ifi.in2000.martiada.oblig2.data.aplacas

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.martiada.oblig2.model.alpacas.PartiesResponse
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://in2000-proxy.ifi.uio.no/alpacaapi/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface AlpacaPartiesDataSource {
    @GET("alpacaparties")
    suspend fun getAlpaca(): PartiesResponse //Dette fordi top level så er hele json responsen et object of ikke en liste
}

object AlpacaPartiesApi {
    val retrofitService: AlpacaPartiesDataSource by lazy {
        retrofit.create(AlpacaPartiesDataSource::class.java)
    }
}