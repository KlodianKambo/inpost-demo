package pl.inpost.recruitmenttask.data.network.model

import com.squareup.moshi.JsonClass
import pl.inpost.recruitmenttask.data.network.model.dto.ShipmentNetworkDto

@JsonClass(generateAdapter = true)
internal data class ShipmentsResponse(
    val shipments: List<ShipmentNetworkDto>
)
