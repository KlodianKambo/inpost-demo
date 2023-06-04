package pl.inpost.recruitmenttask.data.network.model.dto

import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
internal data class EventLogNetworkDto(
    val name: String,
    val date: ZonedDateTime
)
