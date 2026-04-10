package no.uio.ifi.in2000.martiada.oblig2.model.alpacas

import kotlinx.serialization.Serializable

@Serializable
data class PartiesResponse(
    val parties: List<PartyInfo>
)