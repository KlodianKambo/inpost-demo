package pl.inpost.recruitmenttask.domain.usecases

import pl.inpost.recruitmenttask.domain.entities.Shipment
import pl.inpost.recruitmenttask.domain.entities.ShipmentSort
import pl.inpost.recruitmenttask.domain.entities.ShipmentStatus
import javax.inject.Inject

interface SortShipments : (ShipmentSort, List<Shipment>) -> List<Shipment>

internal class SortShipmentsUseCase @Inject constructor() : SortShipments {

    override operator fun invoke(sort: ShipmentSort, shipments: List<Shipment>): List<Shipment> {
        return when (sort) {
            ShipmentSort.Status -> shipments.sortedWith(statusComparator)
            ShipmentSort.PickupDate -> shipments.sortedByDescending { it.pickUpDate }
            ShipmentSort.ExpirationDate -> shipments.sortedByDescending { it.expiryDate }
            ShipmentSort.StoredDate -> shipments.sortedByDescending { it.storedDate }
            ShipmentSort.Number -> shipments.sortedBy { it.number }
        }
    }

    /**
     * private fun
     */
    private val statusComparator = Comparator<Shipment> { shipment1, shipment2 ->
        val statusOrder = listOf(
            ShipmentStatus.CREATED,
            ShipmentStatus.CONFIRMED,
            ShipmentStatus.ADOPTED_AT_SOURCE_BRANCH,
            ShipmentStatus.SENT_FROM_SOURCE_BRANCH,
            ShipmentStatus.ADOPTED_AT_SORTING_CENTER,
            ShipmentStatus.SENT_FROM_SORTING_CENTER,
            ShipmentStatus.OTHER,
            ShipmentStatus.DELIVERED,
            ShipmentStatus.RETURNED_TO_SENDER,
            ShipmentStatus.AVIZO,
            ShipmentStatus.OUT_FOR_DELIVERY,
            ShipmentStatus.READY_TO_PICKUP,
            ShipmentStatus.PICKUP_TIME_EXPIRED
        )

        val statusIndex1 = statusOrder.indexOf(shipment1.status)
        val statusIndex2 = statusOrder.indexOf(shipment2.status)

        when {
            statusIndex1 < statusIndex2 -> -1
            statusIndex1 > statusIndex2 -> 1
            else -> 0
        }
    }
}