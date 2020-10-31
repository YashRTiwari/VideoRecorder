package tech.yashtiwari.videorecorder.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MediaDAO {

    @Query("SELECT * FROM media")
    fun getAll(): LiveData<List<Media>>

    @Query("SELECT * FROM media WHERE name LIKE :fileName")
    suspend fun getMediaByName(fileName: String): Media?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(media: Media)

    @Delete
    suspend fun delete(media: Media)

    @Query("DELETE FROM media")
    suspend fun deleteAll()


}