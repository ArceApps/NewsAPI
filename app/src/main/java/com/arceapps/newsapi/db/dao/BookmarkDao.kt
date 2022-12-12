package com.arceapps.newsapi.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arceapps.newsapi.db.BookmarkModel

/**
 * Created by ArceApps on 08/12/2022.
 * Project name: NewsAPI.
 */
@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(bookmark: BookmarkModel)

    @Query("SELECT * FROM bookmark")
    fun getBookmarks(): LiveData<List<BookmarkModel>>

    @Query("DELETE FROM bookmark WHERE title=:title")
    fun removeBookmark(title: String)

    @Query("SELECT * FROM bookmark WHERE title=:title")
    fun checkBookmark(title: String): List<BookmarkModel>


}