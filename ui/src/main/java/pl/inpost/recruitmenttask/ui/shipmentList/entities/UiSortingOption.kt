package pl.inpost.recruitmenttask.ui.shipmentList.entities

import androidx.annotation.StringRes
import pl.inpost.recruitmenttask.ui.R

sealed class UiSortingOption(@StringRes val titleResId: Int) {
    object Status : UiSortingOption(R.string.sorting_option_status)
    object PickupDate : UiSortingOption(R.string.sorting_option_pickup_date)
    object ExpirationDate : UiSortingOption(R.string.sorting_option_expiration_date)
    object StoredDate : UiSortingOption(R.string.sorting_option_stored_date)
    object Number : UiSortingOption(R.string.sorting_option_number)
}