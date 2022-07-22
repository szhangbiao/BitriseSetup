package com.szhangbiao.storage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.szhangbiao.storage.BuildConfig
import com.szhangbiao.storage.db.converter.DateTimeConverter
import com.szhangbiao.storage.db.dao.ProductDao
import com.szhangbiao.storage.db.dao.UserDao
import com.szhangbiao.storage.db.entity.Product
import com.szhangbiao.storage.db.entity.User
import com.szhangbiao.storage.db.migration.RoomMigrations

@TypeConverters(DateTimeConverter::class)
@Database(entities = [Product::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "Database.db"

        @Volatile
        var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                .apply {
                    if (BuildConfig.DEBUG) {
                        fallbackToDestructiveMigration()
                    } else {
                        addMigrations(*RoomMigrations.migrations)
                    }
                }.build()
    }

    abstract fun userDao(): UserDao

    abstract fun productDao(): ProductDao
}
