package pl.inpost.recruitmenttask.domain.usecases

import pl.inpost.recruitmenttask.domain.repositories.ShipmentLocalStorage
import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository
import javax.inject.Inject

interface FetchShipmentAndPersistIfEmpty : suspend () -> Unit

internal class FetchShipmentAndPersistIfEmptyUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository,
    private val shipmentLocalStorage: ShipmentLocalStorage
) : FetchShipmentAndPersistIfEmpty {
    override suspend operator fun invoke() {
        if (shipmentLocalStorage.count() == 0) {
            shipmentRepository
                .getShipments()
                .let { shipmentLocalStorage.insertList(it) }
        }
    }
}