package no.uio.ifi.in2000.martiada.oblig2.data.votes

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.martiada.oblig2.model.votes.District
import no.uio.ifi.in2000.martiada.oblig2.model.votes.DistrictVotes
import no.uio.ifi.in2000.martiada.oblig2.model.votes.VotesResponse
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://in2000-proxy.ifi.uio.no/alpacaapi/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface AggregatedRawVotesDatasource {
    @GET("district3")
    suspend fun getAggregatedVotes(): VotesResponse
}

class AggregatedVotesDatasource(
    private val rawDatasource: AggregatedRawVotesDatasource =
        retrofit.create(AggregatedRawVotesDatasource::class.java)
) {

    suspend fun getAggregatedVotes(): List<DistrictVotes> {
        val rawData = rawDatasource.getAggregatedVotes().parties

        val convertedVotes = rawData.map {
            DistrictVotes(
                district = District.DISTRICT3,
                alpacaPartyId = it.partyId,
                numberOfVotes = it.votes
            )
        }
        return convertedVotes
    }
}

object AggregatedVotesApi {
    val datasource: AggregatedVotesDatasource by lazy {
        AggregatedVotesDatasource()
    }
}