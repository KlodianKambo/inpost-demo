package pl.inpost.recruitmenttask.domain.usecases

import pl.inpost.recruitmenttask.domain.entities.ShipmentNetwork
import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository
import javax.inject.Inject

interface GetShipmentList {
    suspend operator fun invoke(): List<ShipmentNetwork>
}


internal class GetShipmentListUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository
): GetShipmentList{
    override suspend operator fun invoke() = shipmentRepository.getShipments()
}