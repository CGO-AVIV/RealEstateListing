package com.cgo.realestatelisting.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.cgo.realestatelisting.domain.model.RealEstate
import com.cgo.realestatelisting.domain.resourceprovider.RealEstateResourceProvider
import com.cgo.realestatelisting.domain.usecases.GetRealEstateListingUseCase
import com.cgo.realestatelisting.domain.usecases.GetRealEstateUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RealEstateViewModel(
    private val getRealEstateListingUseCase: GetRealEstateListingUseCase,
    private val getRealEstateUseCase: GetRealEstateUseCase,
    private val resourceProvider: RealEstateResourceProvider
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state.distinctUntilChanged()

    private val _singleState = MutableLiveData<SingleState>()
    val singleState: LiveData<SingleState>
        get() = _singleState.distinctUntilChanged()

    init {
        onRealEstateListingRequested()
    }

    fun onRealEstateListingRequested() {
        _state.postValue(State.Loading)
        viewModelScope.launch {
            getRealEstateListingUseCase.getRealEstateListing()
                .catch { e ->
                    e.printStackTrace()
                    _state.postValue(State.Error(resourceProvider.errorLabel))
                }
                .collect { items ->
                    if (items.isEmpty()) {
                        _state.postValue(State.Error(resourceProvider.emptyLabel))
                    } else {
                        _state.postValue(State.ListContent(items))
                    }
                }
        }
    }

    fun onRealEstateItemRequested(id: Int) {
        _singleState.postValue(SingleState.Loading)
        viewModelScope.launch {
            getRealEstateUseCase.getRealEstate(id)
                .catch { e ->
                    e.printStackTrace()
                    _singleState.postValue(SingleState.Error(resourceProvider.errorLabel))
                }
                .collect { item ->
                    _singleState.postValue(SingleState.SingleContent(item))
                }
        }
    }
}

sealed class State {
    object Loading : State()
    data class ListContent(val items: List<RealEstate>) : State()
    data class Error(val message: String) : State()
}

sealed class SingleState {
    object Loading : SingleState()
    data class SingleContent(val item: RealEstate) : SingleState()
    data class Error(val message: String) : SingleState()
}