package com.szhangbiao.storage.preference

import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocalStorageMobileImplTest {

    private lateinit var localStorage: LocalStorageMobileImpl

    @Before
    fun setUp() {
        localStorage = LocalStorageMobileImpl(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        localStorage.clearData()
    }

    @Test
    fun testOldPreferenceClean() {
    }

    @Test
    fun testOldPreferenceMigration() {
    }
}
