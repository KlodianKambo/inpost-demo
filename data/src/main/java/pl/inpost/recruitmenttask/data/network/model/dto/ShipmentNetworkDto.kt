package pl.inpost.recruitmenttask.data.network.model.dto

import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
internal data class ShipmentNetworkDto(
    val number: String,
    val shipmentType: ShipmentTypeDto,
    val status: ShipmentStatusDto,
    val eventLog: List<EventLogNetworkDto>,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: CustomerNetworkDto?,
    val sender: CustomerNetworkDto?,
    val operations: OperationsNetworkDto
)
