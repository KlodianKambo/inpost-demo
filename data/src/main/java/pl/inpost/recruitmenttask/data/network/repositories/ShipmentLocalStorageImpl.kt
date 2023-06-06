package pl.inpost.recruitmenttask.data.network.repositories

import pl.inpost.recruitmenttask.data.network.daos.ShipmentDao
import pl.inpost.recruitmenttask.data.network.model.roomentities.*
import pl.inpost.recruitmenttask.domain.entities.Customer
import pl.inpost.recruitmenttask.domain.entities.EventLog
import pl.inpost.recruitmenttask.domain.entities.Operations
import pl.inpost.recruitmenttask.domain.entities.Shipment
import pl.inpost.recruitmenttask.domain.repositories.ShipmentLocalStorage
import javax.inject.Inject

internal class ShipmentLocalStorageImpl @Inject constructor(
    private val shipmentDao: ShipmentDao
) : ShipmentLocalStorage {

    override suspend fun insertList(shipmentList: List<Shipment>) {

        shipmentList.map { shipmentNetwork ->
            val shipmentEntity = shipmentNetwork.toShipmentNetwork()
            shipmentDao.insertShipment(shipmentEntity)
            shipmentNetwork.eventLog
                .map { it.toEventLogNetwork(shipmentNetwork.number) }
                .also { shipmentDao.insertEventLogs(it) }
        }
    }

    override suspend fun get(): List<Shipment> {
        return shipmentDao.getAllShipments().map { it.toShipmentNetwork() }
    }


    /***********************************************************************************************
     * Private
     **********************************************************************************************/
    private fun ShipmentWithEventLogs.toShipmentNetwork(): Shipment {
        return Shipment(
            number = this.shipment.number,
            shipmentType = this.shipment.shipmentType,
            status = this.shipment.status,
            eventLog = this.eventLogs.map { it.toEventLogNetwork() },
            openCode = this.shipment.openCode,
            expiryDate = this.shipment.expiryDate,
            storedDate = this.shipment.storedDate,
            pickUpDate = this.shipment.pickUpDate,
            receiver = this.shipment.receiver?.toCustomerNetwork(),
            sender = this.shipment.sender?.toCustomerNetwork(),
            operations = this.shipment.operations.toOperationsNetwork(),
            archived = this.shipment.archived
        )
    }

    private fun EventLogEntity.toEventLogNetwork(): EventLog {
        return EventLog(
            name = name,
            date = date
        )
    }

    private fun CustomerEntity.toCustomerNetwork(): Customer {
        return Customer(email, phoneNumber, name)
    }

    private fun OperationsEntity.toOperationsNetwork(): Operations {
        return Operations(
            manualArchive = manualArchive,
            delete = delete,
            collect = collect,
            highlight = highlight,
            expandAvizo = expandAvizo,
            endOfWeekCollection = endOfWeekCollection,
        )
    }


    private fun Shipment.toShipmentNetwork(): ShipmentEntity {
        return ShipmentEntity(
            number = number,
            shipmentType = shipmentType,
            status = status,
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

    private fun EventLog.toEventLogNetwork(shipmentNumber: String): EventLogEntity {
        return EventLogEntity(
            shipmentNumber = shipmentNumber,
            name = name,
            date = date
        )
    }

    private fun Customer.toCustomerNetwork(): CustomerEntity {
        return CustomerEntity(
            email = email, phoneNumber = phoneNumber, name = name
        )
    }

    private fun Operations.toOperationsNetwork(): OperationsEntity {
        return OperationsEntity(
            manualArchive = manualArchive,
            delete = delete,
            collect = collect,
            highlight = highlight,
            expandAvizo = expandAvizo,
            endOfWeekCollection = endOfWeekCollection,
        )
    }
}


