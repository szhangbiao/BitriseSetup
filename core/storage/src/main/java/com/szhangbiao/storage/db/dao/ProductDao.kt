package com.szhangbiao.storage.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szhangbiao.storage.db.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("SELECT * FROM product")
    suspend fun getProducts(): Flow<List<Product>>

    @Query("SELECT SUM( quantity * price ) from product")
    suspend fun getCartAmount(): Flow<Double>

    @Delete
    suspend fun delete(product: Product)

    @Delete
    suspend fun deleteAll()
}
