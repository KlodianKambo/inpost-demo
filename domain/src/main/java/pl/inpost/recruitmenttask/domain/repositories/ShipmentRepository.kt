package pl.inpost.recruitmenttask.domain.repositories

import pl.inpost.recruitmenttask.domain.entities.Shipment

interface ShipmentRepository {
    suspend fun getShipments(): List<Shipment>
}
