package no.uio.ifi.in2000.martiada.oblig2.data.votes

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.martiada.oblig2.model.votes.District
import no.uio.ifi.in2000.martiada.oblig2.model.votes.DistrictVotes
import no.uio.ifi.in2000.martiada.oblig2.model.votes.IndividualVoteClass
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://in2000-proxy.ifi.uio.no/alpacaapi/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface IndividualVotesRawDatasource {
    @GET("district1")
    suspend fun getIndividualVotesDistrict1(): List<IndividualVoteClass>

    @GET("district2")
    suspend fun getIndividualVotesDistrict2(): List<IndividualVoteClass>
}

class IndividualVotesDatasource(
    private val rawDataSource: IndividualVotesRawDatasource =
        retrofit.create(IndividualVotesRawDatasource::class.java)
) {

    suspend fun getDistrictVotes(district: District): List<DistrictVotes> {
        val rawVotes = when (district) {
            District.DISTRICT1 -> rawDataSource.getIndividualVotesDistrict1()
            District.DISTRICT2 -> rawDataSource.getIndividualVotesDistrict2()
            District.DISTRICT3 -> error("District 3 uses aggregated data, not individual votes")
        }

        return convertToDistrictVotes(rawVotes, district)
    }


    private fun convertToDistrictVotes(
        rawVotesList: List<IndividualVoteClass>,
        district: District
    ): List<DistrictVotes> {

        return rawVotesList
            .groupBy { it.id }
            .map { (id, votes) ->
                DistrictVotes(
                    district = district,
                    alpacaPartyId = id,
                    numberOfVotes = votes.size
                )
            }
    }
}

object IndividualVotesApi {
    val datasource: IndividualVotesDatasource by lazy {
        IndividualVotesDatasource()
    }
}

