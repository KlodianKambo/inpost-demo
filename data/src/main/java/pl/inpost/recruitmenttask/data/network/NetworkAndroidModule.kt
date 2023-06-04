package pl.inpost.recruitmenttask.data.network

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.network.api.MockShipmentApi
import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository

@InstallIn(SingletonComponent::class)
@Module
internal class NetworkAndroidModule {

    @Provides
    fun shipmentApi(@ApplicationContext context: Context, moshi: Moshi): ShipmentRepository = MockShipmentApi(context, moshi)

    @Provides
    fun provideMoshi(zonedDateTimeMoshiAdapter: ZonedDateTimeMoshiAdapter) : Moshi {
        return Moshi.Builder()
            .add(zonedDateTimeMoshiAdapter)
            .build()
    }
}
