package pl.inpost.recruitmenttask.domain.usecases

import pl.inpost.recruitmenttask.domain.repositories.ShipmentLocalStorage
import javax.inject.Inject

interface ArchiveShipment : suspend (String) -> Unit

internal class ArchiveShipmentUseCase @Inject constructor(
    private val shipmentLocalStorage: ShipmentLocalStorage
) : ArchiveShipment {
    override suspend fun invoke(number: String) {
        shipmentLocalStorage.getShipment(number)
            ?.let { shipmentLocalStorage.update(it.copy(archived = true)) }
    }
}