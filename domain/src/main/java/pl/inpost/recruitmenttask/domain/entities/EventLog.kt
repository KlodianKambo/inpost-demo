package pl.inpost.recruitmenttask.domain.entities

import java.time.ZonedDateTime

data class EventLog(
    val name: String,
    val date: ZonedDateTime
)
