package pl.inpost.recruitmenttask.data.network.model.localstorage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
internal data class CustomerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val email: String?,
    val phoneNumber: String?,
    val name: String?
)
