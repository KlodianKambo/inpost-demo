package pl.inpost.recruitmenttask.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.inpost.recruitmenttask.data.network.daos.ShipmentDao
import pl.inpost.recruitmenttask.data.network.model.localstorage.*

@Database(
    entities = [ShipmentEntity::class,
        CustomerEntity::class,
        EventLogEntity::class,
        OperationsEntity::class],
    version = 1
)
@TypeConverters(ZonedDateTimeConverter::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun shipmentDao(): ShipmentDao
}

