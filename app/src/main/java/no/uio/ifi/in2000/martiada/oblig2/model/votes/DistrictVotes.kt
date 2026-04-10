package no.uio.ifi.in2000.martiada.oblig2.model.votes

data class DistrictVotes(
    val district: District?,
    val alpacaPartyId: String,
    val numberOfVotes: Int
)