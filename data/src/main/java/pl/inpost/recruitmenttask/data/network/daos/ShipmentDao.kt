package pl.inpost.recruitmenttask.data.network.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.network.model.roomentities.EventLogEntity
import pl.inpost.recruitmenttask.data.network.model.roomentities.ShipmentEntity
import pl.inpost.recruitmenttask.data.network.model.roomentities.ShipmentWithEventLogs

@Dao
internal interface ShipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShipment(shipment: ShipmentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventLogs(eventLogEntity: List<EventLogEntity>)

    @Query("SELECT * FROM shipments WHERE archived = :archived")
    fun getAllShipments(archived: Boolean): Flow<List<ShipmentWithEventLogs>>

    @Update
    suspend fun updateShipment(shipmentEntity: ShipmentEntity)

    @Query("SELECT COUNT(*) FROM shipments")
    suspend fun getShipmentCount(): Int

    @Query("SELECT * FROM shipments WHERE number = :shipmentNumber")
    suspend fun getShipmentByNumber(shipmentNumber: String): ShipmentWithEventLogs?

}
