package com.arceapps.newsapi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arceapps.newsapi.db.dao.BookmarkDao

/**
 * Created by ArceApps on 08/12/2022.
 * Project name: NewsAPI.
 */
@Database(entities = [BookmarkModel::class], version = 1 )
abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun bookmarkDao() : BookmarkDao

    companion object{
        @Volatile private var instance : BookmarkDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,
            BookmarkDatabase::class.java,
            "bookmark"
        ).allowMainThreadQueries().build()
    }


}