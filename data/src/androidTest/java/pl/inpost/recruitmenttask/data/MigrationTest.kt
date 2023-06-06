package pl.inpost.recruitmenttask.data

import androidx.room.testing.MigrationTestHelper
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.inpost.recruitmenttask.data.network.localstorage.AppDatabase
import pl.inpost.recruitmenttask.data.network.migration_1_2
import pl.inpost.recruitmenttask.data.network.model.roomentities.CustomerEntity
import pl.inpost.recruitmenttask.data.network.model.roomentities.OperationsEntity
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val TEST_DB_NAME = "test-database"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        // Create the version 1 of the database
        val db = helper.createDatabase(TEST_DB_NAME, 1)

        // Insert test data into the version 1 schema
        val receiver = CustomerEntity(
            id = 1,
            email = "email@example.com",
            phoneNumber = "+123456789",
            name = "Receiver Name"
        )
        val sender = CustomerEntity(
            id = 2,
            email = "sender@example.com",
            phoneNumber = "+987654321",
            name = "Sender Name"
        )
        val operations = OperationsEntity(
            id = 1,
            manualArchive = false,
            delete = false,
            collect = false,
            highlight = false,
            expandAvizo = false,
            endOfWeekCollection = false
        )

        db.execSQL("INSERT INTO shipments (number, shipmentType, status, openCode, expiryDate, storedDate, pickUpDate, receiver_email, receiver_phoneNumber, receiver_name, sender_email, sender_phoneNumber, sender_name, operations_id, operations_manualArchive, operations_delete, operations_collect, operations_highlight, operations_expandAvizo, operations_endOfWeekCollection) " +
                "VALUES ('123', 'PARCEL_LOCKER', 'CREATED', 'code', '2023-07-01T12:00:00Z', '2023-06-01T14:00:00Z', '2023-06-02T08:00:00Z', " +
                "'${receiver.email}', '${receiver.phoneNumber}', '${receiver.name}', '${sender.email}', '${sender.phoneNumber}', '${sender.name}', " +
                "'${operations.id}','${operations.manualArchive}', '${operations.delete}', '${operations.collect}', '${operations.highlight}', '${operations.expandAvizo}', '${operations.endOfWeekCollection}')")

        // Close the database before migration
        db.close()

        // Run the migration to version 2
        val migratedDb = helper.runMigrationsAndValidate(TEST_DB_NAME, 2, true, migration_1_2)

        // Verify the migrated data
        migratedDb.query("SELECT * FROM shipments").use { cursor ->
            assertThat(cursor.count, equalTo(1))
            assertThat(cursor.moveToFirst(), equalTo(true))

            val archivedIndex = cursor.getColumnIndex("archived")
            assertThat(cursor.getInt(archivedIndex), equalTo(0))
        }
    }

}
