package pl.inpost.recruitmenttask.ui.shipmentList.entities

data class UiOperationsNetwork(
    val manualArchive: Boolean,
    val delete: Boolean,
    val collect: Boolean,
    val highlight: Boolean,
    val expandAvizo: Boolean,
    val endOfWeekCollection: Boolean
)