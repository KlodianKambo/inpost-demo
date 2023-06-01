package pl.inpost.recruitmenttask.data.network.model

import pl.inpost.recruitmenttask.data.network.model.dto.ShipmentNetworkDto

internal data class ShipmentsResponse(
    val shipments: List<ShipmentNetworkDto>
)
