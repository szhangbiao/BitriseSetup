package com.szhangbiao.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.TEXT)
    val productId: String,
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    val productName: String,
    @ColumnInfo(name = "price", typeAffinity = ColumnInfo.REAL)
    val price: Double,
    @ColumnInfo(name = "quantity", typeAffinity = ColumnInfo.INTEGER)
    val quantity: Int,
    @ColumnInfo(name = "created")
    val created: DateTime,
    @ColumnInfo(name = "updated")
    val updated: DateTime
)
