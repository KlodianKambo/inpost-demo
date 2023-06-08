package pl.inpost.recruitmenttask.data.network.repositories

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import pl.inpost.recruitmenttask.data.R
import pl.inpost.recruitmenttask.data.network.model.dto.ShipmentsResponseDto
import pl.inpost.recruitmenttask.data.network.model.dto.CustomerDto
import pl.inpost.recruitmenttask.data.network.model.dto.EventLogDto
import pl.inpost.recruitmenttask.data.network.model.dto.OperationsDto
import pl.inpost.recruitmenttask.data.network.model.dto.ShipmentDto
import pl.inpost.recruitmenttask.data.network.model.dto.ShipmentStatusDto
import pl.inpost.recruitmenttask.data.network.model.dto.ShipmentTypeDto
import pl.inpost.recruitmenttask.domain.entities.*
import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository
import java.time.ZonedDateTime
import kotlin.random.Random

internal class ShipmentRepositoryMockImpl(
    @ApplicationContext private val context: Context,
    moshi: Moshi
) : ShipmentRepository {

    private val response by lazy {
        val json = context.resources.openRawResource(R.raw.mock_shipment_api_response)
            .bufferedReader()
            .use { it.readText() }
        moshi.adapter(ShipmentsResponseDto::class.java).fromJson(json) as ShipmentsResponseDto
    }

    override suspend fun getShipments(): List<Shipment> {
        delay(1000)
        return response.shipments.map { it.toShipmentNetwork(archived = false) }
    }

    private fun ShipmentDto.toShipmentNetwork(archived: Boolean): Shipment {
        return Shipment(
            number = number,
            shipmentType = shipmentType.toShipmentTypeDto(),
            status = status.toShipmentStatus(),
            eventLog = eventLog.map { it.toEventLogNetwork() },
            openCode = openCode,
            expiryDate = expiryDate,
            storedDate = storedDate,
            pickUpDate = pickUpDate,
            receiver = receiver?.toCustomerNetwork(),
            sender = sender?.toCustomerNetwork(),
            operations = operations.toOperationsNetwork(),
            archived = archived
        )
    }

    private fun ShipmentTypeDto.toShipmentTypeDto(): ShipmentType {
        return when (this) {
            ShipmentTypeDto.PARCEL_LOCKER -> ShipmentType.PARCEL_LOCKER
            ShipmentTypeDto.COURIER -> ShipmentType.COURIER
        }
    }

    private fun EventLogDto.toEventLogNetwork(): EventLog {
        return EventLog(
            name = name,
            date = date
        )
    }

    private fun CustomerDto.toCustomerNetwork(): Customer {
        return Customer(email, phoneNumber, name)
    }

    private fun OperationsDto.toOperationsNetwork(): Operations {
        return Operations(
            manualArchive = manualArchive,
            delete = delete,
            collect = collect,
            highlight = highlight,
            expandAvizo = expandAvizo,
            endOfWeekCollection = endOfWeekCollection,
        )
    }

    private fun ShipmentStatusDto.toShipmentStatus(): ShipmentStatus {
        return when (this) {
            ShipmentStatusDto.ADOPTED_AT_SORTING_CENTER -> ShipmentStatus.ADOPTED_AT_SORTING_CENTER
            ShipmentStatusDto.SENT_FROM_SORTING_CENTER -> ShipmentStatus.SENT_FROM_SORTING_CENTER
            ShipmentStatusDto.ADOPTED_AT_SOURCE_BRANCH -> ShipmentStatus.ADOPTED_AT_SOURCE_BRANCH
            ShipmentStatusDto.SENT_FROM_SOURCE_BRANCH -> ShipmentStatus.SENT_FROM_SOURCE_BRANCH
            ShipmentStatusDto.AVIZO -> ShipmentStatus.AVIZO
            ShipmentStatusDto.CONFIRMED -> ShipmentStatus.CONFIRMED
            ShipmentStatusDto.CREATED -> ShipmentStatus.CREATED
            ShipmentStatusDto.DELIVERED -> ShipmentStatus.DELIVERED
            ShipmentStatusDto.OTHER -> ShipmentStatus.OTHER
            ShipmentStatusDto.OUT_FOR_DELIVERY -> ShipmentStatus.OUT_FOR_DELIVERY
            ShipmentStatusDto.PICKUP_TIME_EXPIRED -> ShipmentStatus.PICKUP_TIME_EXPIRED
            ShipmentStatusDto.READY_TO_PICKUP -> ShipmentStatus.READY_TO_PICKUP
            ShipmentStatusDto.RETURNED_TO_SENDER -> ShipmentStatus.RETURNED_TO_SENDER
            ShipmentStatusDto.NOT_READY -> ShipmentStatus.NOT_READY
        }
    }
}

private fun mockShipmentNetwork(
    number: String = Random.nextLong(1, 9999_9999_9999_9999).toString(),
    type: ShipmentType = ShipmentType.PARCEL_LOCKER,
    status: ShipmentStatus = ShipmentStatus.DELIVERED,
    sender: Customer? = mockCustomerNetwork(),
    receiver: Customer? = mockCustomerNetwork(),
    operations: Operations = mockOperationsNetwork(),
    eventLog: List<EventLog> = emptyList(),
    openCode: String? = null,
    expireDate: ZonedDateTime? = null,
    storedDate: ZonedDateTime? = null,
    pickupDate: ZonedDateTime? = null,
    archived: Boolean = false
) = Shipment(
    number = number,
    shipmentType = type,
    status = status,
    eventLog = eventLog,
    openCode = openCode,
    expiryDate = expireDate,
    storedDate = storedDate,
    pickUpDate = pickupDate,
    receiver = receiver,
    sender = sender,
    operations = operations,
    archived = archived
)

private fun mockCustomerNetwork(
    email: String = "name@email.com",
    phoneNumber: String = "123 123 123",
    name: String = "Jan Kowalski"
) = Customer(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)

private fun mockOperationsNetwork(
    manualArchive: Boolean = false,
    delete: Boolean = false,
    collect: Boolean = false,
    highlight: Boolean = false,
    expandAvizo: Boolean = false,
    endOfWeekCollection: Boolean = false
) = Operations(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection
)
