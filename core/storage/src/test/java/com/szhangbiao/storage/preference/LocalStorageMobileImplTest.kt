package com.szhangbiao.storage.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/test/AndroidManifest.xml")
class LocalStorageMobileImplTest {

    private var localStorage: LocalStorageMobileImpl? = null
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        localStorage = LocalStorageMobileImpl(context)
    }

    @After
    fun tearDown() {
        localStorage?.clearData()
    }

    @Test
    fun testOldPreferenceClean() {
        val oldPreferences: SharedPreferences = context.getSharedPreferences(
            LocalStorageMobileImpl.SHARED_PREF,
            Context.MODE_PRIVATE
        )
        // adding some value
        oldPreferences.edit().putString("SOME_KEY", "SOME_VALUE").commit()
        // getting current data stored
        val allValuesOld = oldPreferences.all
        // should be one value
        Assert.assertEquals(1, allValuesOld.size)
        // Initialize the Local Storage Implementation
        localStorage = LocalStorageMobileImpl(context)
        // getting the old shared preferences data
        val allValues = localStorage?.oldSharedPreferences?.all
        // checking that data is empty
        Assert.assertEquals(0, allValues?.size)
    }

    @Test
    fun testOldPreferenceMigration() {
        val oldPreferences: SharedPreferences = context.getSharedPreferences(
            LocalStorageMobileImpl.SHARED_PREF,
            Context.MODE_PRIVATE
        )
        // adding some values
        oldPreferences.edit {
            putInt("SOME_INT", 123)
            putLong("SOME_LONG", 123_456L)
            putBoolean("SOME_BOOL", true)
            putString("SOME_STRING", "SOME_VALUE")
        }
        // getting current data stored
        val allValuesOld = oldPreferences.all
        // should be one value
        Assert.assertEquals(4, allValuesOld.size)
        // Initialize the Local Storage Implementation
        localStorage = LocalStorageMobileImpl(context)
        // getting the old shared preferences data
        val allValuesOldAfter = localStorage?.oldSharedPreferences?.all
        val allValuesAfter = localStorage?.sharedPreferences?.all

        Assert.assertEquals(0, allValuesOldAfter?.size)
        Assert.assertEquals(4, allValuesAfter?.size)

        Assert.assertEquals(123, localStorage?.sharedPreferences?.getInt("SOME_INT", 0))
        Assert.assertEquals(123_456L, localStorage?.sharedPreferences?.getLong("SOME_LONG", 0L))
        Assert.assertTrue(localStorage?.sharedPreferences?.getBoolean("SOME_BOOL", false) == true)
        Assert.assertEquals("SOME_VALUE", localStorage?.sharedPreferences?.getString("SOME_STRING", ""))
    }

    @Test
    fun setDeviceSerialNumber() {
        val mockValue = "mock"
        localStorage?.deviceSerialNumber = mockValue
        Assert.assertEquals("Device serial number is:", mockValue, localStorage?.sharedPreferences?.getString("SERIAL_NUMBER", null))
    }

    @Test
    fun getDeviceSerialNumber() {
        val mockValue = "mock"
        localStorage?.sharedPreferences?.edit { putString("SERIAL_NUMBER", mockValue) }
        Assert.assertEquals("Device serial number is:", mockValue, localStorage?.deviceSerialNumber)
    }

    @Test
    fun getUserId() {
        val mockValue = 1
        localStorage?.sharedPreferences?.edit { putInt("USER_ID", mockValue) }
        Assert.assertEquals("User Id is:", mockValue, localStorage?.userId)
    }

    @Test
    fun getUserIdDefault() {
        Assert.assertEquals("User Id is:", 0, localStorage?.userId)
    }

    @Test
    fun setUserId() {
        val mockValue = 1
        localStorage?.userId = mockValue
        Assert.assertEquals("User Id is:", mockValue, localStorage?.sharedPreferences?.getInt("USER_ID", 0))
    }

    @Test
    fun getLastBuyBackEmail() {
        val mockValue = "mock"
        localStorage?.sharedPreferences?.edit { putString("BUYBACK_EMAIL", mockValue) }
        Assert.assertEquals("Last buyback email is:", mockValue, localStorage?.lastBuyBackEmail)
    }

    @Test
    fun setLastBuyBackEmail() {
        val mockValue = "mock"
        localStorage?.lastBuyBackEmail = mockValue
        Assert.assertEquals("Last buyback email is:", mockValue, localStorage?.sharedPreferences?.getString("BUYBACK_EMAIL", null))
    }

    @Test
    fun getUserName() {
        val mockValue = "mock"
        localStorage?.sharedPreferences?.edit { putString("USER_NAME", mockValue) }
        Assert.assertEquals("User name is:", mockValue, localStorage?.userName)
    }

    @Test
    fun setUserName() {
        val mockValue = "mock"
        localStorage?.userName = mockValue
        Assert.assertEquals("User name is:", mockValue, localStorage?.sharedPreferences?.getString("USER_NAME", null))
    }

    @Test
    fun getInitialSetupTime() {
        val mockValue = 1L
        localStorage?.sharedPreferences?.edit { putLong("INITIAL_SETUP_TIME", mockValue) }
        Assert.assertEquals("initial setup time is:", mockValue, localStorage?.initialSetupTime)
    }

    @Test
    fun setInitialSetupTime() {
        val mockValue = 1L
        localStorage?.initialSetupTime = mockValue
        Assert.assertEquals("initial setup time is:", mockValue, localStorage?.sharedPreferences?.getLong("INITIAL_SETUP_TIME", 0L))
    }

    @Test
    fun getLastUserSession() {
        val mockValue = 1L
        localStorage?.sharedPreferences?.edit { putLong("LAST_USER_SESSION", mockValue) }
        Assert.assertEquals("last user session is:", mockValue, localStorage?.lastUserSession)
    }

    @Test
    fun setLastUserSession() {
        val mockValue = 1L
        localStorage?.lastUserSession = mockValue
        Assert.assertEquals("last user session is:", mockValue, localStorage?.sharedPreferences?.getLong("LAST_USER_SESSION", 0L))
    }

    @Test
    fun getDarkModeEnabled() {
        val mockValue = true
        localStorage?.sharedPreferences?.edit { putBoolean("DARK_MODE", mockValue) }
        Assert.assertEquals("Dark mode is:", mockValue, localStorage?.getDarkModeEnabled())
    }

    @Test
    fun toggleDarkMode() {
        localStorage?.sharedPreferences?.edit { putBoolean("DARK_MODE", false) }
        localStorage?.toggleDarkMode()
        Assert.assertEquals("Dark mode is:", true, localStorage?.sharedPreferences?.getBoolean("DARK_MODE", false))
    }

    @Test
    fun testCleanData() {
        val mockUserId = 1
        localStorage?.userId = mockUserId
        val mockLastBuyBackEmail = "a@ba.com"
        localStorage?.lastBuyBackEmail = mockLastBuyBackEmail
        val mockUserName = "mock"
        localStorage?.userName = mockUserName
        val mockInitialSetupTime = 1L
        localStorage?.initialSetupTime = mockInitialSetupTime
        val mockLastUserSession = 1L
        localStorage?.lastUserSession = mockLastUserSession

        val allValues = localStorage?.sharedPreferences?.all
        Assert.assertTrue((allValues?.size ?: 0) > 0)

        localStorage?.clearData()
        val allValuesAfterClean = localStorage?.sharedPreferences?.all
        Assert.assertEquals(0, allValuesAfterClean?.size)
    }
}
