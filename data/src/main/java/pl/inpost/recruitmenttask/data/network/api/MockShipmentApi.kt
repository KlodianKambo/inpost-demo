package pl.inpost.recruitmenttask.data.network.api

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import pl.inpost.recruitmenttask.data.R
import pl.inpost.recruitmenttask.data.network.model.ShipmentsResponse
import pl.inpost.recruitmenttask.data.network.model.dto.*
import pl.inpost.recruitmenttask.domain.entities.*
import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository
import java.time.ZonedDateTime
import kotlin.random.Random

internal class MockShipmentApi(
    @ApplicationContext private val context: Context,
    moshi: Moshi
) : ShipmentRepository {

    private val response by lazy {
        val json = context.resources.openRawResource(R.raw.mock_shipment_api_response)
            .bufferedReader()
            .use { it.readText() }
        moshi.adapter(ShipmentsResponse::class.java).fromJson(json) as ShipmentsResponse
    }

    override suspend fun getShipments(): List<ShipmentNetwork> {
        delay(1000)
        return response.shipments.map { it.toShipmentNetwork() }
    }

    private fun ShipmentNetworkDto.toShipmentNetwork(): ShipmentNetwork {
        return ShipmentNetwork(
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
            operations = operations.toOperationsNetwork()
        )
    }

    private fun ShipmentTypeDto.toShipmentTypeDto(): ShipmentType {
        return when (this) {
            ShipmentTypeDto.PARCEL_LOCKER -> ShipmentType.PARCEL_LOCKER
            ShipmentTypeDto.COURIER -> ShipmentType.COURIER
        }
    }

    private fun EventLogNetworkDto.toEventLogNetwork(): EventLogNetwork {
        return EventLogNetwork(
            name = name,
            date = date
        )
    }

    private fun CustomerNetworkDto.toCustomerNetwork(): CustomerNetwork {
        return CustomerNetwork(email, phoneNumber, name)
    }

    private fun OperationsNetworkDto.toOperationsNetwork(): OperationsNetwork {
        return OperationsNetwork(
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
    sender: CustomerNetwork? = mockCustomerNetwork(),
    receiver: CustomerNetwork? = mockCustomerNetwork(),
    operations: OperationsNetwork = mockOperationsNetwork(),
    eventLog: List<EventLogNetwork> = emptyList(),
    openCode: String? = null,
    expireDate: ZonedDateTime? = null,
    storedDate: ZonedDateTime? = null,
    pickupDate: ZonedDateTime? = null
) = ShipmentNetwork(
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
    operations = operations
)

private fun mockCustomerNetwork(
    email: String = "name@email.com",
    phoneNumber: String = "123 123 123",
    name: String = "Jan Kowalski"
) = CustomerNetwork(
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
) = OperationsNetwork(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection
)
