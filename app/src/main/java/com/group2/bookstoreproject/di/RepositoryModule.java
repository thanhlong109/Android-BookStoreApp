package com.group2.bookstoreproject.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

//    @Binds
//    @Singleton
//    public abstract  bindDoctorRepository(doctorRepositoryImpl: DoctorRepositoryImpl): DoctorRepository
}
