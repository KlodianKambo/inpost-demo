package pl.inpost.recruitmenttask.data.network.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ShipmentsResponseDto(
    val shipments: List<ShipmentDto>
)
