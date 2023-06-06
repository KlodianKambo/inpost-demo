package pl.inpost.recruitmenttask.data.network.model.localstorage

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import pl.inpost.recruitmenttask.domain.entities.ShipmentStatus
import pl.inpost.recruitmenttask.domain.entities.ShipmentType
import java.time.ZonedDateTime

@Entity(tableName = "shipments")
internal data class ShipmentEntity(
    @PrimaryKey val number: String,
    val shipmentType: ShipmentType,
    val status: ShipmentStatus,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    @Embedded(prefix = "receiver_") val receiver: CustomerEntity?,
    @Embedded(prefix = "sender_") val sender: CustomerEntity?,
    @Embedded(prefix = "operations_") val operations: OperationsEntity
)