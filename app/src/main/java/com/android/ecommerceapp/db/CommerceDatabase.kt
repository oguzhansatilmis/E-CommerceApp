package com.android.ecommerceapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.ecommerceapp.model.FavoritesEntity

@Entity
@Database(entities = [FavoritesEntity::class], version =1, exportSchema = false)
@TypeConverters(RatingConverter::class)
abstract class CommerceDatabase:RoomDatabase() {

    abstract fun moduleDao():ModuleDao

    companion object {
        @Volatile
        private var instance: CommerceDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CommerceDatabase::class.java,
            "commerce_database"
        ).build()
    }
}