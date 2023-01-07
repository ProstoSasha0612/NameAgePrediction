package com.projectapp.nameageprediction.presentation.di

import com.projectapp.nameageprediction.data.RepositoryImpl
import com.projectapp.nameageprediction.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @AppScope
    fun bindRepositoryImplToRepository(repositoryImpl: RepositoryImpl): Repository

}
