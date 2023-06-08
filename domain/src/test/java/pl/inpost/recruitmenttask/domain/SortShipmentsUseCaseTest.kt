package pl.inpost.recruitmenttask.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import pl.inpost.recruitmenttask.domain.entities.*
import pl.inpost.recruitmenttask.domain.usecases.SortShipmentsUseCase
import java.time.ZonedDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SortShipmentsUseCaseTest {

    private val sortShipmentsUseCase = SortShipmentsUseCase()

    @Test
    fun `assert sort by package number`() {
        val shipmentList = listOf(
            mockShipment(number = "5"),
            mockShipment(number = "4"),
            mockShipment(number = "3"),
            mockShipment(number = "2"),
            mockShipment(number = "1"),
        )

        val expected = listOf("1", "2", "3", "4", "5")
        val result = sortShipmentsUseCase.invoke(sort = ShipmentSort.Number, shipmentList)
        assertEquals(expected, result.map { it.number })
    }


    @Test
    fun `assert sort by storedDate`() {
        val shipmentList = listOf(
            mockShipment(number = "1", storedDate = ZonedDateTime.now()),
            mockShipment(number = "4", storedDate = ZonedDateTime.now().minusHours(3)),
            mockShipment(number = "3", storedDate = ZonedDateTime.now().minusHours(2)),
            mockShipment(number = "5", storedDate = ZonedDateTime.now().minusHours(4)),
            mockShipment(number = "2", storedDate = ZonedDateTime.now().minusHours(1)),
        )

        val expected = listOf("1", "2", "3", "4", "5")
        val result = sortShipmentsUseCase.invoke(sort = ShipmentSort.StoredDate, shipmentList)

        assertEquals(expected, result.map { it.number })
    }

    @Test
    fun `assert sort by pickupDate`() {
        val shipmentList = listOf(
            mockShipment(number = "1", pickUpDate = ZonedDateTime.now()),
            mockShipment(number = "4", pickUpDate = ZonedDateTime.now().minusHours(3)),
            mockShipment(number = "3", pickUpDate = ZonedDateTime.now().minusHours(2)),
            mockShipment(number = "5", pickUpDate = ZonedDateTime.now().minusHours(4)),
            mockShipment(number = "2", pickUpDate = ZonedDateTime.now().minusHours(1)),
        )

        val expected = listOf("1", "2", "3", "4", "5")
        val result = sortShipmentsUseCase.invoke(sort = ShipmentSort.PickupDate, shipmentList)

        assertEquals(expected, result.map { it.number })
    }

    @Test
    fun `assert sort by expiredDate`() {
        val shipmentList = listOf(
            mockShipment(number = "1", expiryDate = ZonedDateTime.now()),
            mockShipment(number = "4", expiryDate = ZonedDateTime.now().minusHours(3)),
            mockShipment(number = "3", expiryDate = ZonedDateTime.now().minusHours(2)),
            mockShipment(number = "5", expiryDate = ZonedDateTime.now().minusHours(4)),
            mockShipment(number = "2", expiryDate = ZonedDateTime.now().minusHours(1)),
        )

        val expected = listOf("1", "2", "3", "4", "5")
        val result = sortShipmentsUseCase.invoke(sort = ShipmentSort.ExpirationDate, shipmentList)

        assertEquals(expected, result.map { it.number })
    }

    /**
     * Order of statuses
     * 1. CREATED
     * 2. CONFIRMED
     * 3. ADOPTED_AT_SOURCE_BRANCH
     * 4. SENT_FROM_SOURCE_BRANCH
     * 5. ADOPTED_AT_SORTING_CENTER
     * 6. SENT_FROM_SORTING_CENTER
     * 7. OTHER
     * 8. DELIVERED
     * 9. RETURNED_TO_SENDER
     * 10. AVIZO
     * 11. OUT_FOR_DELIVERY
     * 12. READY_TO_PICKUP
     * 13. PICKUP_TIME_EXPIRED
     */
    @Test
    fun `assert sort by status `() {
        val shipmentList = listOf(
            mockShipment(number = "2", ShipmentStatus.CONFIRMED),
            mockShipment(number = "4", ShipmentStatus.SENT_FROM_SOURCE_BRANCH),
            mockShipment(number = "3", ShipmentStatus.ADOPTED_AT_SOURCE_BRANCH),
            mockShipment(number = "8", ShipmentStatus.DELIVERED),
            mockShipment(number = "11", ShipmentStatus.OUT_FOR_DELIVERY),
            mockShipment(number = "6", ShipmentStatus.SENT_FROM_SORTING_CENTER),
            mockShipment(number = "7", ShipmentStatus.OTHER),
            mockShipment(number = "5", ShipmentStatus.ADOPTED_AT_SORTING_CENTER),
            mockShipment(number = "1", ShipmentStatus.CREATED),
            mockShipment(number = "10", ShipmentStatus.AVIZO),
            mockShipment(number = "13", ShipmentStatus.PICKUP_TIME_EXPIRED),
            mockShipment(number = "12", ShipmentStatus.READY_TO_PICKUP),
            mockShipment(number = "9", ShipmentStatus.RETURNED_TO_SENDER),
        )

        val expected = (1..13).map { it.toString() }
        val result = sortShipmentsUseCase.invoke(sort = ShipmentSort.Status, shipmentList)

        assertEquals(expected, result.map { it.number })
    }


    /***********************************************************************************************
     * Private fun
     **********************************************************************************************/
    private fun mockOperations() = Operations(false, false, false, false, false, false)
    private fun mockEventLog() = listOf(EventLog("", ZonedDateTime.now()))
    private fun mockShipment(
        number: String = "1",
        status: ShipmentStatus = ShipmentStatus.NOT_READY,
        expiryDate: ZonedDateTime? = null,
        storedDate: ZonedDateTime? = null,
        pickUpDate: ZonedDateTime? = null,
    ): Shipment {
        return Shipment(
            number = number,
            shipmentType = ShipmentType.COURIER,
            status = status,
            eventLog = mockEventLog(),
            openCode = "openCode",
            expiryDate = expiryDate,
            storedDate = storedDate,
            pickUpDate = pickUpDate,
            receiver = null,
            sender = null,
            operations = mockOperations(),
            archived = false,
        )
    }
}