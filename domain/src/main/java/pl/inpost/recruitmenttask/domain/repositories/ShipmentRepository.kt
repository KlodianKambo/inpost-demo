package pl.inpost.recruitmenttask.domain.repositories

import pl.inpost.recruitmenttask.domain.entities.ShipmentNetwork

interface ShipmentRepository {
    suspend fun getShipments(): List<ShipmentNetwork>
}
