package tech.yashtiwari.videorecorder.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media")
data class Media(@PrimaryKey val id: Int,
                 @ColumnInfo(name = "file_name") val name: String?,
                 @ColumnInfo(name = "file_path") val path: String?)