package com.gs.test.data.database

import androidx.room.*
import com.gs.test.data.model.LikedItem

@Dao
interface LikedItemsDao {

    @Query("Select * from liked_items ORDER BY date(date) DESC")
    suspend fun getAll(): List<LikedItem>


    @Query("Delete from liked_items")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(t: List<LikedItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecords(t: List<LikedItem>)

    @Delete
    suspend fun deleteRecord(t: LikedItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecord(t: LikedItem)
}