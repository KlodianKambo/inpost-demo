package pl.inpost.recruitmenttask.ui.shipmentList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.domain.entities.ShipmentNetwork
import pl.inpost.recruitmenttask.domain.usecases.GetShipmentList
import pl.inpost.recruitmenttask.util.setState
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val getShipmentList: GetShipmentList
) : ViewModel() {

    private val mutableViewState = MutableLiveData<List<ShipmentNetwork>>(emptyList())
    val viewState: LiveData<List<ShipmentNetwork>> = mutableViewState

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch(Dispatchers.Main) {
            val shipments = getShipmentList()
            mutableViewState.setState { shipments }
        }
    }
}
