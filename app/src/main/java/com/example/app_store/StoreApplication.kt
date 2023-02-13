package com.example.app_store

import android.app.Application
import androidx.room.Room
import com.example.app_store.Database.StoreDatabase


/*
* Sinngleton pattern implementation
* */
class StoreApplication: Application() {
    companion object{
        lateinit var database:StoreDatabase
    }

    override fun onCreate() {
        super.onCreate()
      database = Room.databaseBuilder(this,StoreDatabase::class.java,"StoreDatabase").build()

    }
}