package pl.inpost.recruitmenttask.domain.di

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.domain.usecases.GetShipmentList
import pl.inpost.recruitmenttask.domain.usecases.GetShipmentListUseCase


@InstallIn(SingletonComponent::class)
@dagger.Module
class UseCaseModule {

    @Provides
    internal fun provideGetShipmentList(impl: GetShipmentListUseCase): GetShipmentList = impl

}