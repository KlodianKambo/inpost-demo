package pl.inpost.recruitmenttask.ui.shipmentList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.domain.entities.*
import pl.inpost.recruitmenttask.domain.usecases.GetShipmentList
import pl.inpost.recruitmenttask.ui.R
import pl.inpost.recruitmenttask.ui.shipmentList.entities.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val getShipmentList: GetShipmentList
) : ViewModel() {

    // TODO the day and the month are to be formatted based on the locale, e.g. for the US and EN
    private val dateFormatter = SimpleDateFormat("| dd.MM.yy | hh.mm", Locale.ROOT)

    private val mutableViewState = MutableSharedFlow<List<UiShipmentNetwork>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val viewState: Flow<List<UiShipmentNetwork>> = mutableViewState

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch(Dispatchers.Main) {
            val shipments = getShipmentList().map { it.toUiShipmentNetwork() }
            mutableViewState.emit(shipments)
        }
    }


    /**
     * Mappers: can be moved outside and made internal if needed in other classes,
     * in order to avoid code duplication (if it fits the feature).
     */
    private fun ShipmentNetwork.toUiShipmentNetwork(): UiShipmentNetwork {
        return UiShipmentNetwork(
            number = number,
            shipmentTypeIconRes = shipmentType.getShipmentTypeImageResId(),
            status = status.getShipmenStatusResId(),
            eventLog = eventLog.map { it.toUiEventLogNetwork() },
            openCode = openCode,
            // TODO use a string pattern
            //  missing the meaning and the logic of "pn."
            receivedFormattedDate = storedDate?.let { "pn. " + dateFormatter.format(Date.from(it.toInstant())) },
            receiver = receiver?.toUiCustomerNetwork(),
            sender = sender?.toUiCustomerNetwork(),
            operations = operations.toUiOperationsNetwork()
        )
    }

    private fun ShipmentType.getShipmentTypeImageResId(): Int {
        return when (this) {
            ShipmentType.PARCEL_LOCKER -> R.drawable.ic_cell
            ShipmentType.COURIER -> R.drawable.ic_courrier
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