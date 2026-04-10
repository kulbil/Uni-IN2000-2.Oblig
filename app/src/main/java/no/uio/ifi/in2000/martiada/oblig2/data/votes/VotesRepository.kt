package no.uio.ifi.in2000.martiada.oblig2.data.votes

import no.uio.ifi.in2000.martiada.oblig2.model.votes.District
import no.uio.ifi.in2000.martiada.oblig2.model.votes.DistrictVotes

class VotesRepository {

    suspend fun getVotes(): List<DistrictVotes> {
        val district1Votes = IndividualVotesApi.datasource.getDistrictVotes(District.DISTRICT1)
        val district2Votes = IndividualVotesApi.datasource.getDistrictVotes(District.DISTRICT2)
        val district3Votes = AggregatedVotesApi.datasource.getAggregatedVotes()

        return listOf(
            district1Votes,
            district2Votes,
            district3Votes
        ).flatten()
    }
}