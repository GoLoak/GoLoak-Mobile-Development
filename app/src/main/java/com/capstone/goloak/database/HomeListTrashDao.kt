package com.capstone.goloak.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.capstone.goloak.model.network.HomeListTrash

@Dao
interface HomeListTrashDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(listTrash: HomeListTrash)

    @Query("SELECT * FROM home_list_trash")
    fun getAllTrash(): LiveData<List<HomeListTrash>>

    @Update
    fun update(listTrash: HomeListTrash)

    @Delete
    fun delete(listTrash: HomeListTrash)
}