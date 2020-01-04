package com.example.watchlist.activities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.watchlist.R
import com.example.watchlist.TestUtils
import com.example.watchlist.fragments.MainFragment
import com.example.watchlist.fragments.SerieListFragment
import com.example.watchlist.persistence.SavedSeriesDatabase
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MainActivityTest  {

    private lateinit var savedSeriesDatabase: SavedSeriesDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockNavController: NavController

    @Before
    fun initDatabase() {


        mockNavController = mock(NavController::class.java)

        savedSeriesDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, SavedSeriesDatabase::class.java).allowMainThreadQueries().build()


        doAsync {
            val series = TestUtils.createMockSeries()
            series.forEach {
                savedSeriesDatabase.savedSerieDao().insert(it)
            }
        }
    }

    @After
    fun closeDb() {
        savedSeriesDatabase.close()
    }

    @Test
    fun navigateToSeriesDetailFromHome() {
        doAsync {

            val scenario = launchFragmentInContainer(themeResId = R.style.Theme_AppCompat) {
                MainFragment().also { fragment ->
                    fragment.viewLifecycleOwnerLiveData.observeForever { owner ->
                        if (owner != null) {
                            Navigation.setViewNavController(fragment.requireView(), mockNavController)
                        }
                    }
                }
            }

            onView(withId(R.id.serie_running_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
            verify(mockNavController).navigate(R.id.action_mainFragment_to_serieDetailFragment)

        }
    }

    @Test
    fun navigateToSeriesDetailFromMySeries() {
        doAsync {
            val scenario = launchFragmentInContainer(themeResId =  R.style.Theme_AppCompat) {
                SerieListFragment().also { fragment ->
                    fragment.viewLifecycleOwnerLiveData.observeForever {owner ->
                        if (owner != null){
                            Navigation.setViewNavController(fragment.requireView(), mockNavController)
                        }
                    }
                }
            }

            onView(withId(R.id.serie_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
            verify(mockNavController).navigate(R.id.action_serieListFragment_to_serieDetailFragment)
        }
    }

}