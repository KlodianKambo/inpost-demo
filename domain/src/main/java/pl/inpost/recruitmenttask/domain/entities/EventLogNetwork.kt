package pl.inpost.recruitmenttask.domain.entities

import java.time.ZonedDateTime

data class EventLogNetwork(
    val name: String,
    val date: ZonedDateTime
)
