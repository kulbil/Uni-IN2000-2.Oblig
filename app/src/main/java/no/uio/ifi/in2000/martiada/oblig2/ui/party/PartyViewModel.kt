package no.uio.ifi.in2000.martiada.oblig2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.martiada.oblig2.data.aplacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.martiada.oblig2.model.alpacas.PartyInfo
import okio.IOException

sealed interface AlpacaPartyUiState {
    data class Success(val info: PartyInfo) : AlpacaPartyUiState
    object Error : AlpacaPartyUiState
    object Loading : AlpacaPartyUiState
}

class PartyViewModel() : ViewModel() {

    private val repo = AlpacaPartiesRepository()

    private val _alpacaPartyUiState =
        MutableStateFlow<AlpacaPartyUiState>(AlpacaPartyUiState.Loading)
    val alpacaPartyUiState = _alpacaPartyUiState.asStateFlow()

    fun getAlpacaPartyInfo(partyId: String) {
        viewModelScope.launch {
            _alpacaPartyUiState.value = try {
                val result = repo.getAlpacaParty(partyId)
                if (result != null) {
                    AlpacaPartyUiState.Success(result)
                } else {
                    AlpacaPartyUiState.Error
                }
            } catch (e: IOException) {
                AlpacaPartyUiState.Error
            }
        }
    }
}