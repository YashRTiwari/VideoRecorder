package tech.yashtiwari.videorecorder.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import tech.yashtiwari.videorecorder.MediaType
import tech.yashtiwari.videorecorder.Utility
import java.util.*


@Entity(tableName = "media")
@Parcelize
data class Media(
    @PrimaryKey(autoGenerate = true) val id: Int,
                 @ColumnInfo(name = "name") val name: String,
                 @ColumnInfo(name = "path") val path: String,
                @ColumnInfo(name="duration") val duration: Int?,
                @ColumnInfo(name="type") val type: String,
                @ColumnInfo(name="time") val time : String = Date().toString()
) : Parcelable