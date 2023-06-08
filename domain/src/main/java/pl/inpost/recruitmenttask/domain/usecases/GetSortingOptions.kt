package pl.inpost.recruitmenttask.domain.usecases

import pl.inpost.recruitmenttask.domain.entities.ShipmentSort
import javax.inject.Inject

interface GetSortingOptions : () -> List<ShipmentSort>

internal class GetSortingOptionsUseCase @Inject constructor() : GetSortingOptions {
    override operator fun invoke(): List<ShipmentSort> {
        return ShipmentSort.values().toList()
    }
}
