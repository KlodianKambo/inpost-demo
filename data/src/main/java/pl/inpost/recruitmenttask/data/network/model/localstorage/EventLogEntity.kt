package pl.inpost.recruitmenttask.data.network.model.localstorage

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(
    tableName = "event_logs",
    foreignKeys = [
        ForeignKey(
            entity = ShipmentEntity::class,
            parentColumns = ["number"],
            childColumns = ["shipmentNumber"],
            onDelete = CASCADE
        )]
)
internal data class EventLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val shipmentNumber: String,
    val name: String,
    val date: ZonedDateTime
)