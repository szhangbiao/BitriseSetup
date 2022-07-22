package com.szhangbiao.storage.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.szhangbiao.storage.db.AppDatabase
import com.szhangbiao.storage.db.DataProvider
import com.szhangbiao.storage.utils.RandomUtil
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class UserDaoTest {

    companion object {
        const val MAX_TEST_COUNT = 1000
        const val DB_NAME = "Test.db"
    }

    private var appDb: AppDatabase? = null
    private var userDao: UserDao? = null

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        appDb = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).build()
        userDao = appDb?.userDao()
    }

    @After
    fun tearDown() {
        runBlocking {
            userDao?.deleteAll()
        }
        appDb = null
    }

    @Test
    fun insert() = runBlocking {
        val testUser = DataProvider.getRandomUser()
        userDao?.insert(testUser)
        val userInDb = userDao?.getAllUsers()?.first()
        Assert.assertEquals(userInDb?.size, 1)
        Assert.assertEquals(testUser, userInDb?.getOrNull(0))
    }

    @Test
    fun insertAll() = runBlocking {
        val testCount = RandomUtil.randomInt(MAX_TEST_COUNT)
        val testUsers = DataProvider.getRandomUsers(testCount)
        userDao?.insertAll(testUsers)
        val usersInDb = userDao?.getAllUsers()?.first()
        Assert.assertEquals(testCount, usersInDb?.size)
        Assert.assertEquals(testUsers, usersInDb)
    }

    @Test
    fun getAllUsers() = runBlocking {
        val testCount = RandomUtil.randomInt(MAX_TEST_COUNT)
        val testUsers = DataProvider.getRandomUsers(testCount)
        userDao?.insertAll(testUsers)
        val usersInDb = userDao?.getAllUsers()?.first()
        Assert.assertEquals(testCount, usersInDb?.size)
    }

    @Test
    fun getUserById() = runBlocking {
        val testCount = RandomUtil.randomInt(MAX_TEST_COUNT)
        val testUsers = DataProvider.getRandomUsers(testCount)
        userDao?.insertAll(testUsers)
        val randomUser = testUsers[RandomUtil.randomInt(testCount)]
        val userInDb = userDao?.getUserById(randomUser.userId)?.first()
        Assert.assertEquals(randomUser, userInDb)
    }

    @Test
    fun delete() = runBlocking {
        val testCount = RandomUtil.randomInt(MAX_TEST_COUNT)
        val testUsers = DataProvider.getRandomUsers(testCount)
        userDao?.insertAll(testUsers)
        val randomUser = testUsers[RandomUtil.randomInt(testCount)]
        var storeUsers = userDao?.getAllUsers()?.first()
        Assert.assertEquals(storeUsers?.size, testCount)
        userDao?.delete(randomUser)
        storeUsers = userDao?.getAllUsers()?.first()
        Assert.assertEquals(storeUsers?.size, testCount - 1)
    }

    @Test
    fun deleteAll() = runBlocking {
        val testCount = RandomUtil.randomInt(MAX_TEST_COUNT)
        val testUsers = DataProvider.getRandomUsers(testCount)
        userDao?.insertAll(testUsers)
        var storeUsers = userDao?.getAllUsers()?.first()
        Assert.assertEquals(storeUsers?.size, testCount)
        userDao?.deleteAll()
        storeUsers = userDao?.getAllUsers()?.first()
        Assert.assertEquals(storeUsers?.size, 0)
    }
}
