package pl.inpost.recruitmenttask.data.network.daos

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.network.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "inpost-app-database"
        ).build()
    }

    @Provides
    fun provideShipmentDao(appDatabase: AppDatabase): ShipmentDao {
        return appDatabase.shipmentDao()
    }
}
