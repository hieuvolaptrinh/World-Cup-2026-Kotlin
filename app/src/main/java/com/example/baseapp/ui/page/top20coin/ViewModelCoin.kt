package com.example.baseapp.ui.page.top20coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseapp.data.remote.Resource
import com.example.baseapp.data.remote.dto.Coin
import com.example.baseapp.domain.usecase.coin.container.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelCoin @Inject constructor(
    private val coinUseCases: CoinUseCases
) : ViewModel() {

    private val _top20State =
        MutableStateFlow<Resource<List<Coin>>>(Resource.Loading())

    val top20State: StateFlow<Resource<List<Coin>>> =
        _top20State.asStateFlow()

    fun getTop20Coins() {
        viewModelScope.launch {
            _top20State.value = Resource.Loading()
            _top20State.value = coinUseCases.getTop20Coin()
        }
    }
}