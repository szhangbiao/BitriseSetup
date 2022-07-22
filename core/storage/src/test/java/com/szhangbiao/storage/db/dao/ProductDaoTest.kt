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
class ProductDaoTest {

    companion object {
        const val MAX_TEST_COUNT = 1000
        const val DB_NAME = "Test.db"
    }

    private var appDb: AppDatabase? = null
    private var productDao: ProductDao? = null

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        appDb = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).build()
        productDao = appDb?.productDao()
    }

    @After
    fun tearDown() {
        runBlocking {
            productDao?.deleteAll()
        }
        appDb = null
    }

    @Test
    fun insert() = runBlocking {
        val testProduct = DataProvider.getRandomProduct()
        productDao?.insert(testProduct)
        val storeProduct = productDao?.getProducts()?.first()
        Assert.assertEquals(storeProduct?.size, 1)
        Assert.assertEquals(testProduct, storeProduct?.getOrNull(0))
    }

    @Test
    fun insertAll() = runBlocking {
        val testCount = RandomUtil.randomInt(MAX_TEST_COUNT)
        val testProducts = DataProvider.getRandomProducts(testCount)
        productDao?.insertAll(testProducts)
        val storeProducts = productDao?.getProducts()?.first()
        Assert.assertEquals(testCount, storeProducts?.size)
        Assert.assertEquals(testProducts, storeProducts)
    }

    @Test
    fun getProducts() = runBlocking {
        val testCount = RandomUtil.randomInt(UserDaoTest.MAX_TEST_COUNT)
        val testProducts = DataProvider.getRandomProducts(testCount)
        productDao?.insertAll(testProducts)
        val storeProducts = productDao?.getProducts()?.first()
        Assert.assertEquals(testCount, storeProducts?.size)
    }

    @Test
    fun getCartAmount() = runBlocking {
        val testCount = RandomUtil.randomInt(UserDaoTest.MAX_TEST_COUNT)
        val testProducts = DataProvider.getRandomProducts(testCount)
        productDao?.insertAll(testProducts)
        var expectedPrice = 0.0
        testProducts.forEach { expectedPrice += it.price * it.quantity }
        val price = productDao?.getCartAmount()?.first()
        assert(expectedPrice == price)
        // Add another product
        val testProduct = DataProvider.getRandomProduct()
        productDao?.insert(testProduct)
        val updatePrice = expectedPrice + (testProduct.quantity * testProduct.price)
        assert(updatePrice == productDao?.getCartAmount()?.first())
    }

    @Test
    fun delete() = runBlocking {
        val testCount = RandomUtil.randomInt(UserDaoTest.MAX_TEST_COUNT)
        val testProducts = DataProvider.getRandomProducts(testCount)
        productDao?.insertAll(testProducts)
        val randomProduct = testProducts[RandomUtil.randomInt(testCount)]
        var storeProducts = productDao?.getProducts()?.first()
        Assert.assertEquals(storeProducts?.size, testCount)
        productDao?.delete(randomProduct)
        storeProducts = productDao?.getProducts()?.first()
        Assert.assertEquals(storeProducts?.size, testCount - 1)
    }

    @Test
    fun deleteAll() = runBlocking {
        val testCount = RandomUtil.randomInt(UserDaoTest.MAX_TEST_COUNT)
        val testProducts = DataProvider.getRandomProducts(testCount)
        productDao?.insertAll(testProducts)
        var storeProducts = productDao?.getProducts()?.first()
        Assert.assertEquals(storeProducts?.size, testCount)
        productDao?.deleteAll()
        storeProducts = productDao?.getProducts()?.first()
        Assert.assertEquals(storeProducts?.size, 0)
    }
}
