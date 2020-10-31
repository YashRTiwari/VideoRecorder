package tech.yashtiwari.videorecorder.db

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class MediaRepository(private val mediaDao: MediaDAO) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allMedia: LiveData<List<Media>> = mediaDao.getAll()

    suspend fun insert(media: Media) {
        mediaDao.insert(media)
    }
}