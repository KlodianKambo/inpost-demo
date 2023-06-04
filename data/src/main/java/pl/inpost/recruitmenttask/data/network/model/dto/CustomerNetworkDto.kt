package pl.inpost.recruitmenttask.data.network.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CustomerNetworkDto(
    val email: String?,
    val phoneNumber: String?,
    val name: String?
)