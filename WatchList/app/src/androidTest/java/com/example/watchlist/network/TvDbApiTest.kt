package com.example.watchlist.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.watchlist.TestUtils
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.utils.API_URL
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class TvDbApiTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    var mockServer: MockWebServer = MockWebServer()

    private lateinit var theTestApi : TVDBApi

    @Before
    fun prep() {
        mockServer.start()
    }

    @After
    fun breakdown() {
        mockServer.close()
    }

    fun getJson(path: String): String {
        // Load the JSON response
        val fileContent = this::class.java.getResource(path).readText()
        return fileContent
    }

    fun createApi() : TVDBApi {

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.addInterceptor(OkHttpProfilerInterceptor())
        }.build()

        return Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build().create(TVDBApi::class.java)

    }

    @Test
    fun builder_CreatesApiInstance() {
        theTestApi = createApi()
        assert(theTestApi != null)
    }


    @Test
    fun api_SeriesAreRetrievedAndMapped() {
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.setBody(getJson("/JSON/MockSeries.JSON"))
        mockedResponse.addHeader("Content-Type", "application/json")
        mockedResponse.throttleBody(1024, 1, TimeUnit.SECONDS)
        mockServer.enqueue(mockedResponse)
        theTestApi = createApi()

        theTestApi.searchSeries("","")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).map {
                assert(it.series.isNotEmpty())
            }

    }

}