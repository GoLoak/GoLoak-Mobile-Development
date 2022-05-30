package com.capstone.goloak.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.capstone.goloak.database.HomeListTrashDao
import com.capstone.goloak.database.HomeListTrashDatabase
import com.capstone.goloak.model.network.HomeListTrash
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomeListTrashRepository(application: Application) {
    private val mHomeListTrashDao: HomeListTrashDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = HomeListTrashDatabase.getDatabase(application)
        mHomeListTrashDao = db.homeListTrashDao()
    }
    fun getAllNotes(): LiveData<List<HomeListTrash>> = mHomeListTrashDao.getAllTrash()
    fun insert(note: HomeListTrash) {
        executorService.execute { mHomeListTrashDao.insert(note) }
    }
    fun delete(note: HomeListTrash) {
        executorService.execute { mHomeListTrashDao.delete(note) }
    }
    fun update(note: HomeListTrash) {
        executorService.execute { mHomeListTrashDao.update(note) }
    }
}