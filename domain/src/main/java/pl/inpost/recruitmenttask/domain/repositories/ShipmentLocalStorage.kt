package pl.inpost.recruitmenttask.domain.repositories

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.domain.entities.Shipment


interface ShipmentLocalStorage {
    suspend fun insertList(shipmentList: List<Shipment>)
    fun getAllShipments(archived: Boolean): Flow<List<Shipment>>
    suspend fun update(shipment: Shipment)
    suspend fun count(): Int
    suspend fun getShipment(number: String): Shipment?
}