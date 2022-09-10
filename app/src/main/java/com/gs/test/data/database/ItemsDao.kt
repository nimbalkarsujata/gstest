package com.gs.test.data.database

import androidx.room.*
import com.gs.test.data.model.Item

@Dao
interface ItemsDao {

    @Query("Select * from item ORDER BY date(date) DESC")
    suspend fun getAll(): List<Item>

    @Query("Select * from item where date = :date")
    suspend fun getItemByDate(date: String): Item

    @Query("Delete from item")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(t: List<Item>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecords(t: List<Item>)

    @Delete
    suspend fun deleteRecord(t: List<Item>)

    @Update
    suspend fun updateRecord(t: Item)
}