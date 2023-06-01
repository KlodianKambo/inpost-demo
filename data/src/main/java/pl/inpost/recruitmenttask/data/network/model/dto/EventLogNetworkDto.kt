package pl.inpost.recruitmenttask.data.network.model.dto

import java.time.ZonedDateTime

internal data class EventLogNetworkDto(
    val name: String,
    val date: ZonedDateTime
)
