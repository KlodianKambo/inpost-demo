package pl.inpost.recruitmenttask.domain.usecases

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.domain.entities.Shipment
import pl.inpost.recruitmenttask.domain.repositories.ShipmentLocalStorage
import javax.inject.Inject

interface GetUnarchivedShipmentList : () -> Flow<List<Shipment>>

internal class GetUnarchivedShipmentListUseCase @Inject constructor(
    private val shipmentLocalStorage: ShipmentLocalStorage
) : GetUnarchivedShipmentList {
    override operator fun invoke(): Flow<List<Shipment>> {
        return shipmentLocalStorage.getAllShipments(false)
    }
}