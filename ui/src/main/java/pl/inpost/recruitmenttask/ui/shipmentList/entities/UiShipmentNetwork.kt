package pl.inpost.recruitmenttask.ui.shipmentList.entities

import androidx.annotation.StringRes
import java.time.ZonedDateTime

data class UiShipmentNetwork(
    val number: String,
    val shipmentType: UiShipmentType,
    @StringRes val status: Int,
    val eventLog: List<UiEventLogNetwork>,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: UiCustomerNetwork?,
    val sender: UiCustomerNetwork?,
    val operations: UiOperationsNetwork
)