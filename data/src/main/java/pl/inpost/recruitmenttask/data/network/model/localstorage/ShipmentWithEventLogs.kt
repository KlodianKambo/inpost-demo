package pl.inpost.recruitmenttask.data.network.model.localstorage

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

internal data class ShipmentWithEventLogs(
    @Embedded val shipment: ShipmentEntity,
    @Relation(parentColumn = "number", entityColumn = "shipmentNumber")
    val eventLogs: List<EventLogEntity>
)