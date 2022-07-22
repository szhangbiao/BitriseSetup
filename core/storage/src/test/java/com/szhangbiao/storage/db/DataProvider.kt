package com.szhangbiao.storage.db

import android.os.Build
import com.szhangbiao.storage.db.entity.Product
import com.szhangbiao.storage.db.entity.User
import org.joda.time.DateTime
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataProvider {

    fun getRandomProduct(): Product = Product(
        productId = randomString(),
        productName = "Name-${randomString()}",
        price = randomDouble(),
        quantity = randomInt(),
        created = DateTime.now(),
        updated = DateTime.now()
    )

    fun getRandomProducts(count: Int): List<Product> {
        val products = mutableListOf<Product>()
        for (i in 0 until count) {
            products.add(getRandomProduct())
        }
        return products
    }

    fun getRandomUser(): User = User(randomString(), "Name-${randomString()}", randomInt(), DateTime.now(), DateTime.now())

    fun getRandomUsers(count: Int): List<User> {
        val users = mutableListOf<User>()
        for (i in 0 until count) {
            users.add(getRandomUser())
        }
        return users
    }

    private fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    private fun randomInt(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ThreadLocalRandom.current().nextInt(0, 1000 + 1)
        } else {
            Random().nextInt(1000 + 1)
        }
    }

    private fun randomDouble(): Double {
        return randomInt().toDouble()
    }
}
