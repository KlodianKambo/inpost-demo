package pl.inpost.recruitmenttask.data.network.daos

import androidx.room.*
import pl.inpost.recruitmenttask.data.network.model.roomentities.EventLogEntity
import pl.inpost.recruitmenttask.data.network.model.roomentities.ShipmentEntity
import pl.inpost.recruitmenttask.data.network.model.roomentities.ShipmentWithEventLogs

@Dao
internal interface ShipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShipment(shipment: ShipmentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventLogs(eventLogEntity: List<EventLogEntity>)

    @Query("SELECT * FROM shipments")
    suspend fun getAllShipments(): List<ShipmentWithEventLogs>
}
