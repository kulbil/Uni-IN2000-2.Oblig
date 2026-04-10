package no.uio.ifi.in2000.martiada.oblig2.data.aplacas

import no.uio.ifi.in2000.martiada.oblig2.data.votes.VotesRepository
import no.uio.ifi.in2000.martiada.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.martiada.oblig2.model.votes.District
import no.uio.ifi.in2000.martiada.oblig2.model.votes.DistrictVotes

val votesRepository = VotesRepository()

class AlpacaPartiesRepository {

    suspend fun getAlpacas(): List<PartyInfo> {
        return AlpacaPartiesApi.retrofitService.getAlpaca().parties
    }

    suspend fun getAlpacaParty(partyId: String): PartyInfo? {
        val parties = getAlpacas()
        return parties.find { it.id == partyId }
    }

    suspend fun getAllVotes(): List<DistrictVotes> {
        return votesRepository.getVotes()
    }

    suspend fun getAllDistrictVotes(district: District): List<DistrictVotes> {
        return votesRepository.getVotes().filter { it.district == district }
    }
}