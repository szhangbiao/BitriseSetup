package com.szhangbiao.storage.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.szhangbiao.storage.db.dao.ProductDao
import com.szhangbiao.storage.db.dao.UserDao
import com.szhangbiao.storage.db.entity.Product
import com.szhangbiao.storage.db.entity.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class AppDatabaseTest {

    private var database: AppDatabase? = null

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = AppDatabase(context)
    }

    @Test
    fun testDatabaseOpen() = runBlocking {
        val userDao: UserDao? = database?.userDao()
        val productDao: ProductDao? = database?.productDao()
        Assert.assertTrue(userDao != null)
        Assert.assertTrue(productDao != null)
        val users: List<User>? = userDao?.getAllUsers()?.first()
        Assert.assertTrue(database?.isOpen ?: false)
        Assert.assertEquals(0, users?.size)
        val products: List<Product>? = productDao?.getProducts()?.first()
        Assert.assertEquals(0, products?.size)
    }
}
