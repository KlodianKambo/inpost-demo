package pl.inpost.recruitmenttask.data.network.model.roomentities

import androidx.room.Embedded
import androidx.room.Relation

internal data class ShipmentWithEventLogs(
    @Embedded val shipment: ShipmentEntity,
    @Relation(parentColumn = "number", entityColumn = "shipmentNumber")
    val eventLogs: List<EventLogEntity>
)