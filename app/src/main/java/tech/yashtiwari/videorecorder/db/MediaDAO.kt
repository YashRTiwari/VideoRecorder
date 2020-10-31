package tech.yashtiwari.videorecorder.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MediaDAO {

    @Query("SELECT * FROM media")
    fun getAll(): List<Media>

    @Query("SELECT * FROM media WHERE file_name LIKE :fileName")
    fun findByName(fileName: String): List<Media>

    @Insert
    fun insert(media: Media)

    @Delete
    fun delete(media: Media)
}