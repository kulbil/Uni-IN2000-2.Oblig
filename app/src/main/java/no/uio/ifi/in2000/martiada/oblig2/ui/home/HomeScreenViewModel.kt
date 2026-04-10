package no.uio.ifi.in2000.martiada.oblig2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.martiada.oblig2.data.aplacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.martiada.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.martiada.oblig2.model.votes.District
import no.uio.ifi.in2000.martiada.oblig2.model.votes.DistrictVotes
import no.uio.ifi.in2000.martiada.oblig2.model.votes.VotesDisplayMethod
import okio.IOException

sealed interface AlpacaUiState {
    data class Success(val info: List<PartyInfo>) : AlpacaUiState
    object Error : AlpacaUiState
    object Loading : AlpacaUiState
}

sealed interface VotesUiState {
    data class Success(val info: List<DistrictVotes>) : VotesUiState
    object Error : VotesUiState
    object Loading : VotesUiState
}

data class VotesDisplayMethodState(
    val displayMethod: VotesDisplayMethod = VotesDisplayMethod.ALL,
    val selectedDistrict: District? = null
)

class HomeScreenViewModel() : ViewModel() {

    private val alpacaRepo = AlpacaPartiesRepository()

    private val _alpacaUiState = MutableStateFlow<AlpacaUiState>(AlpacaUiState.Loading)
    val alpacaUiState = _alpacaUiState.asStateFlow()

    private val _votesUiState = MutableStateFlow<VotesUiState>(VotesUiState.Loading)
    val votesUiState = _votesUiState.asStateFlow()

    private val _votesDisplayMethodState =
        MutableStateFlow<VotesDisplayMethodState>(VotesDisplayMethodState())
    val votesDisplayMethodState = _votesDisplayMethodState.asStateFlow()

    init {
        getAlpacaInfo()
        getAllVotes()
    }

    fun getAlpacaInfo() {
        viewModelScope.launch {
            _alpacaUiState.value = try {
                val result = alpacaRepo.getAlpacas()
                AlpacaUiState.Success(result)
            } catch (e: IOException) {
                AlpacaUiState.Error
            }
        }
    }

    fun getAllVotes() {
        viewModelScope.launch {
            _votesUiState.value = try {
                val result = alpacaRepo.getAllVotes()
                    .groupBy { it.alpacaPartyId }
                    .map { (partyId, votes) ->
                        DistrictVotes(
                            district = null, //Litt sleip måte men dette funker :)
                            alpacaPartyId = partyId,
                            numberOfVotes = votes.sumOf { it.numberOfVotes }
                        )
                    }
                VotesUiState.Success(result)
            } catch (e: IOException) {
                VotesUiState.Error
            }
        }
    }

    fun getAllDistrictVotes(district: District) {
        viewModelScope.launch {
            _votesUiState.value = try {
                val result = alpacaRepo.getAllDistrictVotes(district)
                VotesUiState.Success(result)
            } catch (e: IOException) {
                VotesUiState.Error
            }
        }
    }

    fun changeVoteDisplay(displayMethod: VotesDisplayMethod) {
        _votesDisplayMethodState.value = VotesDisplayMethodState(
            displayMethod = displayMethod,
        )

        //Litt hjelp av KI her
        when (displayMethod) {
            VotesDisplayMethod.ALL -> getAllVotes()
            VotesDisplayMethod.DISTRICT1 -> getAllDistrictVotes(District.DISTRICT1)
            VotesDisplayMethod.DISTRICT2 -> getAllDistrictVotes(District.DISTRICT2)
            VotesDisplayMethod.DISTRICT3 -> getAllDistrictVotes(District.DISTRICT3)
        }
    }
}