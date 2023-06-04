package pl.inpost.recruitmenttask.ui.shipmentList

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.domain.entities.*
import pl.inpost.recruitmenttask.domain.entities.ShipmentNetwork
import pl.inpost.recruitmenttask.domain.usecases.GetShipmentList
import pl.inpost.recruitmenttask.ui.R
import pl.inpost.recruitmenttask.ui.shipmentList.entities.*
import pl.inpost.recruitmenttask.util.setState
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val getShipmentList: GetShipmentList
) : ViewModel() {

    private val mutableViewState = MutableLiveData<List<UiShipmentNetwork>>(emptyList())
    val viewState: LiveData<List<UiShipmentNetwork>> = mutableViewState

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch(Dispatchers.Main) {
            val shipments = getShipmentList().map { it.toUiShipmentNetwork() }
            mutableViewState.setState { shipments }
        }
    }


    /**
     * Mappers: can be moved outside and made internal if needed in other classes,
     * in order to avoid code duplication (if it fits the feature).
     */
    private fun ShipmentNetwork.toUiShipmentNetwork(): UiShipmentNetwork {
        return UiShipmentNetwork(
            number = number,
            shipmentType = shipmentType.toUiShipmentType(),
            status = status.getShipmenStatusResId(),
            eventLog = eventLog.map { it.toUiEventLogNetwork() },
            openCode = openCode,
            expiryDate = expiryDate,
            storedDate = storedDate,
            pickUpDate = pickUpDate,
            receiver = receiver?.toUiCustomerNetwork(),
            sender = sender?.toUiCustomerNetwork(),
            operations = operations.toUiOperationsNetwork()
        )
    }

    private fun ShipmentType.toUiShipmentType(): UiShipmentType {
        return when (this) {
            ShipmentType.PARCEL_LOCKER -> UiShipmentType.PARCEL_LOCKER
            ShipmentType.COURIER -> UiShipmentType.COURIER
        }
    }

    private fun EventLogNetwork.toUiEventLogNetwork(): UiEventLogNetwork {
        return UiEventLogNetwork(
            name = name,
            date = date
        )
    }

    private fun CustomerNetwork.toUiCustomerNetwork(): UiCustomerNetwork {
        return UiCustomerNetwork(email, phoneNumber, name)
    }

    private fun OperationsNetwork.toUiOperationsNetwork(): UiOperationsNetwork {
        return UiOperationsNetwork(
            manualArchive = manualArchive,
            delete = delete,
            collect = collect,
            highlight = highlight,
            expandAvizo = expandAvizo,
            endOfWeekCollection = endOfWeekCollection,
        )
    }

    private fun ShipmentStatus.getShipmenStatusResId(): Int {
        return when (this) {
            ShipmentStatus.ADOPTED_AT_SORTING_CENTER -> UiShipmentStatus.ADOPTED_AT_SORTING_CENTER
            ShipmentStatus.SENT_FROM_SORTING_CENTER -> UiShipmentStatus.SENT_FROM_SORTING_CENTER
            ShipmentStatus.ADOPTED_AT_SOURCE_BRANCH -> UiShipmentStatus.ADOPTED_AT_SOURCE_BRANCH
            ShipmentStatus.SENT_FROM_SOURCE_BRANCH -> UiShipmentStatus.SENT_FROM_SOURCE_BRANCH
            ShipmentStatus.AVIZO -> UiShipmentStatus.AVIZO
            ShipmentStatus.CONFIRMED -> UiShipmentStatus.CONFIRMED
            ShipmentStatus.CREATED -> UiShipmentStatus.CREATED
            ShipmentStatus.DELIVERED -> UiShipmentStatus.DELIVERED
            ShipmentStatus.OTHER -> UiShipmentStatus.OTHER
            ShipmentStatus.OUT_FOR_DELIVERY -> UiShipmentStatus.OUT_FOR_DELIVERY
            ShipmentStatus.PICKUP_TIME_EXPIRED -> UiShipmentStatus.PICKUP_TIME_EXPIRED
            ShipmentStatus.READY_TO_PICKUP -> UiShipmentStatus.READY_TO_PICKUP
            ShipmentStatus.RETURNED_TO_SENDER -> UiShipmentStatus.RETURNED_TO_SENDER
            ShipmentStatus.NOT_READY -> UiShipmentStatus.NOT_READY
        }.nameRes
    }
}