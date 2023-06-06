package pl.inpost.recruitmenttask.domain.usecases

import pl.inpost.recruitmenttask.domain.entities.Shipment
import pl.inpost.recruitmenttask.domain.repositories.ShipmentLocalStorage
import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository
import javax.inject.Inject

interface GetShipmentList {
    suspend operator fun invoke(): List<Shipment>
}


internal class GetShipmentListUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository,
    private val shipmentLocalStorage: ShipmentLocalStorage
) : GetShipmentList {
    override suspend operator fun invoke(): List<Shipment> {
        return shipmentLocalStorage.get()
            .takeIf { it.isNotEmpty() }
            ?: run {
                shipmentRepository
                    .getShipments()
                    .apply { shipmentLocalStorage.insertList(this) }
            }
    }
}