package com.szhangbiao.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.TEXT)
    val userId: String,
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    val userName: String,
    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.INTEGER)
    val age: Int,
    @ColumnInfo(name = "created")
    val created: DateTime,
    @ColumnInfo(name = "updated")
    val updated: DateTime
)