package tech.yashtiwari.videorecorder.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import tech.yashtiwari.videorecorder.MediaType
import tech.yashtiwari.videorecorder.Utility

@Entity(tableName = "media")
data class Media(@PrimaryKey val id: Int,
                 @ColumnInfo(name = "name") val name: String,
                 @ColumnInfo(name = "path") val path: String)