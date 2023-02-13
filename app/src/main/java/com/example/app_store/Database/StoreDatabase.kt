package com.example.app_store.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app_store.models.StoreDao
import com.example.app_store.models.StoreEntity

@Database(entities = arrayOf(StoreEntity::class), version = 1)
abstract class StoreDatabase : RoomDatabase(){

abstract fun storeDao():StoreDao

}