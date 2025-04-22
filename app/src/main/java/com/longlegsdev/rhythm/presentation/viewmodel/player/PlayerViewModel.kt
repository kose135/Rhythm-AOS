package com.longlegsdev.rhythm.presentation.viewmodel.player

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.service.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerManager: MusicPlayerManager,
) : ViewModel() {

    val playbackState = playerManager.playbackState
    val playbackEvent = playerManager.playbackEvents
    val isPlay = playerManager.isPlaying
    val currentIndex = playerManager.currentIndex
    val currentMusic = playerManager.currentMusic
    val musicList = playerManager.musicList
    val currentPosition = playerManager.currentPosition
    val bufferedPosition = playerManager.bufferedPosition
    val duration = playerManager.duration

    fun play(musicList: List<MusicEntity>, index: Int) {
        playerManager.play(musicList, index)
    }

    fun play(index: Int) {
        playerManager.play(index)
    }

    fun playPause() = playerManager.playPause()

    fun next() = playerManager.next()

    fun previous() = playerManager.previous()

    fun seekTo(positionMs: Long) = playerManager.seekTo(positionMs)

    private val _albumBitmap = MutableStateFlow<Bitmap?>(null)
    val albumBitmap: StateFlow<Bitmap?> = _albumBitmap.asStateFlow()

    fun extractAlbumArt(url: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = try {
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(url, HashMap())
                val picture = retriever.embeddedPicture
                retriever.release()

                picture?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
                    ?: BitmapFactory.decodeResource(context.resources, R.drawable.ic_cover)
            } catch (e: Exception) {
                Timber.e(e)
                BitmapFactory.decodeResource(context.resources, R.drawable.ic_cover)
            }
            _albumBitmap.value = bitmap
        }
    }

}