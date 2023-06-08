package pl.inpost.recruitmenttask.data.network.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.network.repositories.ShipmentLocalStorageImpl
import pl.inpost.recruitmenttask.data.network.ZonedDateTimeMoshiAdapter
import pl.inpost.recruitmenttask.data.network.repositories.ShipmentRepositoryMockImpl
import pl.inpost.recruitmenttask.domain.repositories.ShipmentLocalStorage
import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository

@InstallIn(SingletonComponent::class)
@Module
internal class NetworkAndroidModule {

    @Provides
    fun shipmentApi(@ApplicationContext context: Context, moshi: Moshi): ShipmentRepository = ShipmentRepositoryMockImpl(context, moshi)

    @Provides
    fun provideMoshi(zonedDateTimeMoshiAdapter: ZonedDateTimeMoshiAdapter) : Moshi {
        return Moshi.Builder()
            .add(zonedDateTimeMoshiAdapter)
            .build()
    }

    @Provides
    fun provideShipmentNetworkLocalStorage(impl: ShipmentLocalStorageImpl): ShipmentLocalStorage = impl
}
