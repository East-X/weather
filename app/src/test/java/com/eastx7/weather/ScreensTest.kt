package com.eastx7.weather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.eastx7.weather.data.db.DbDay
import com.eastx7.weather.data.db.DbDayDao
import com.github.takahirom.roborazzi.captureRoboImage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.util.concurrent.atomic.AtomicBoolean

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class ScreensTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var dbDayDao: DbDayDao
    val initialized = AtomicBoolean(false)
    val scope = CoroutineScope(Job() + Dispatchers.IO)

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
                .build()
        dbDayDao = appDatabase.dbDayDao()

        scope.launch {
            appDatabase.dbDayDao().insert(
                DbDay(
                    id = 1707228000,
                    epoch = 2024037L,
                    cloudCover = 75,
                    pressure = 978,
                    humidity = 92,
                    temperatureMinimum = -18.5,
                    temperatureMaximum = -12.3,
                    windSpeed = 5.2
                )
            )
            initialized.set(true)
        }
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @get:Rule
    val composeRule = createComposeRule()

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltrule.inject()
    }

    @Test
    fun testDbDayDao() {
        scope.launch {
            assertNotNull(dbDayDao.getById(1707228000))
            assertEquals(dbDayDao.listOfItems(5).first().size, 1)
        }
    }

    @Test
    fun testDaysListScreen() {
        ActivityScenario.launch(MainActivity::class.java)
            .use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    composeRule
                        .onNodeWithTag("days_list_body").assertIsDisplayed()
                }
            }
    }

    @Test
    fun roborazziDaysListScreen() {
        ActivityScenario.launch(MainActivity::class.java)
            .use { scenario ->
                scenario.onActivity { activity: MainActivity ->
                    composeRule
                        .onNodeWithTag("days_list_body")
                        .onParent()
                        .captureRoboImage("src/test/snapshots/images/days_list_body.png")
                }
            }
    }
}