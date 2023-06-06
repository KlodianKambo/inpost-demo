package pl.inpost.recruitmenttask.data.network.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
            val shipmentEntity = shipmentNetwork.toShipmentEntity()
            shipmentDao.insertShipment(shipmentEntity)
            shipmentNetwork.eventLog
                .map { it.toEventLogEntity(shipmentNetwork.number) }
                .also { shipmentDao.insertEventLogs(it) }
        }
    }

    override fun getAllShipments(archived: Boolean): Flow<List<Shipment>> {
        return shipmentDao.getAllShipments(archived)
            .map { it.map { it.toShipment() } }
    }

    override suspend fun update(shipment: Shipment) {
        shipmentDao.updateShipment(shipment.toShipmentEntity())
    }

    override suspend fun count(): Int {
        return shipmentDao.getShipmentCount()
    }

    override suspend fun getShipment(number: String): Shipment? {
        return shipmentDao.getShipmentByNumber(number)?.toShipment()
    }


    /***********************************************************************************************
     * Private
     **********************************************************************************************/
    private fun ShipmentWithEventLogs.toShipment(): Shipment {
        return Shipment(
            number = this.shipment.number,
            shipmentType = this.shipment.shipmentType,
            status = this.shipment.status,
            eventLog = this.eventLogs.map { it.toEventLog() },
            openCode = this.shipment.openCode,
            expiryDate = this.shipment.expiryDate,
            storedDate = this.shipment.storedDate,
            pickUpDate = this.shipment.pickUpDate,
            receiver = this.shipment.receiver?.toCustomer(),
            sender = this.shipment.sender?.toCustomer(),
            operations = this.shipment.operations.toOperations(),
            archived = this.shipment.archived
        )
    }

    private fun EventLogEntity.toEventLog(): EventLog {
        return EventLog(
            name = name,
            date = date
        )
    }

    private fun CustomerEntity.toCustomer(): Customer {
        return Customer(email, phoneNumber, name)
    }

    private fun OperationsEntity.toOperations(): Operations {
        return Operations(
            manualArchive = manualArchive,
            delete = delete,
            collect = collect,
            highlight = highlight,
            expandAvizo = expandAvizo,
            endOfWeekCollection = endOfWeekCollection,
        )
    }


    private fun Shipment.toShipmentEntity(): ShipmentEntity {
        return ShipmentEntity(
            number = number,
            shipmentType = shipmentType,
            status = status,
            openCode = openCode,
            expiryDate = expiryDate,
            storedDate = storedDate,
            pickUpDate = pickUpDate,
            receiver = receiver?.toCustomerEntity(),
            sender = sender?.toCustomerEntity(),
            operations = operations.toOperationsEntity(),
            archived = archived
        )
    }

    private fun EventLog.toEventLogEntity(shipmentNumber: String): EventLogEntity {
        return EventLogEntity(
            shipmentNumber = shipmentNumber,
            name = name,
            date = date
        )
    }

    private fun Customer.toCustomerEntity(): CustomerEntity {
        return CustomerEntity(
            email = email, phoneNumber = phoneNumber, name = name
        )
    }

    private fun Operations.toOperationsEntity(): OperationsEntity {
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


