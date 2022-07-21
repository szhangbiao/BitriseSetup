package com.szhangbiao.storage.db.dao

import androidx.room.*
import com.szhangbiao.storage.db.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: String): Flow<User?>

    @Delete
    suspend fun delete(user: User)

    @Delete
    suspend fun deleteAll()
}