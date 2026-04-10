package no.uio.ifi.in2000.martiada.oblig2.model.votes

import kotlinx.serialization.Serializable

@Serializable
data class AggregatedVotesClass(
    val partyId: String,
    val votes: Int
)