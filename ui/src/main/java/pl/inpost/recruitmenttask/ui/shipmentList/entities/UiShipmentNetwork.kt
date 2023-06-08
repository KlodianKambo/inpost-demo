package pl.inpost.recruitmenttask.ui.shipmentList.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class UiShipmentNetwork(
    val number: String,
    @DrawableRes val shipmentTypeIconRes: Int,
    @StringRes val status: Int,
    val eventLog: List<UiEventLogNetwork>,
    val openCode: String?,
    val receivedFormattedDate: String?,
    val receiver: UiCustomerNetwork?,
    val sender: UiCustomerNetwork?,
    val operations: UiOperationsNetwork
)