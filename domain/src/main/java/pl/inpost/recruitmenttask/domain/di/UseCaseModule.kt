package pl.inpost.recruitmenttask.domain.di

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.domain.usecases.*


@InstallIn(SingletonComponent::class)
@dagger.Module
class UseCaseModule {

    @Provides
    internal fun provideFetchShipmentAndPersistIfEmpty(impl: FetchShipmentAndPersistIfEmptyUseCase): FetchShipmentAndPersistIfEmpty =
        impl

    @Provides
    internal fun provideGetShipmentList(impl: GetUnarchivedShipmentListUseCase): GetUnarchivedShipmentList =
        impl

    @Provides
    internal fun provideUpdateShipment(impl: ArchiveShipmentUseCase): ArchiveShipment = impl

}