package tech.yashtiwari.videorecorder.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Media::class), version = 1, exportSchema = false)
public abstract class MediaDatabase : RoomDatabase() {

    abstract fun mediaDao(): MediaDAO

    private class MediaDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var mediaDAO = database.mediaDao()

                    // Delete all content here.
                    //mediaDAO.deleteAll()

                    // Add sample words.
                    var word = Media(1,"Hello", "Path")
                    mediaDAO.insert(word)

                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MediaDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MediaDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MediaDatabase::class.java,
                    "media_database"
                )
                    .addCallback(MediaDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}