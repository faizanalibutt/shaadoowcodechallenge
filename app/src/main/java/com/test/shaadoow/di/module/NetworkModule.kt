package com.test.shaadoow.di.module

import com.test.shaadoow.network.ShaadoowApi
import com.test.shaadoow.util.Constants
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Module which provides all required dependencies about network
 */
@Module
object NetworkModule {
    /**
     * Provides the Artists/Feeds service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Artists/Feeds service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideApi(retrofit: Retrofit): ShaadoowApi {
        return retrofit.create(ShaadoowApi::class.java)
    }
    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}