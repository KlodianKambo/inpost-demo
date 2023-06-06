package pl.inpost.recruitmenttask.domain.repositories

import pl.inpost.recruitmenttask.domain.entities.Shipment


interface ShipmentLocalStorage {
    suspend fun insertList(shipmentList: List<Shipment>)
    suspend fun get(): List<Shipment>
}