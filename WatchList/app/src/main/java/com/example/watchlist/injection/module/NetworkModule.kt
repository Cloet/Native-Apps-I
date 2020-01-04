package com.example.watchlist.injection.module

import com.example.watchlist.network.TVDBApi
import com.example.watchlist.utils.API_URL
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    /**
     * Provides the api.
     * @param retrofit the retrofit object used to instantiate the service.
     * */
    @Provides
    @Singleton
    internal fun provideTVDBApi(retrofit: Retrofit): TVDBApi {
        return retrofit.create(TVDBApi::class.java)
    }

    /**
     * Creates a retrofit instance.
     * @return [Retrofit]*/
    @Provides
    @Singleton
    internal fun provideRetrofitInterface(): Retrofit {

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.addInterceptor(OkHttpProfilerInterceptor())
        }.build()

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

    }

}