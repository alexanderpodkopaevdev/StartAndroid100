package com.example.p1261mediaplayer

import android.content.ContentUris
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_MUSIC
import android.media.AudioAttributes.USAGE_MEDIA
import android.media.AudioManager
import android.media.AudioManager.STREAM_MUSIC
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


class MainActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    companion object {
        const val LOG_TAG = "myLogs"

        const val DATA_HTTP = "http://kdg.htmlweb.ru/music/mark_bernes_-_zhuravli.mp3"
        const val DATA_STREAM = "http://online.radiorecord.ru:8101/rr_128"
        val DATA_SD =
            "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)} /music.mp3"
        val DATA_URI = ContentUris.withAppendedId(
            android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            13359
        )
    }

    var mediaPlayer: MediaPlayer? = null
    lateinit var am: AudioManager
    override fun onDestroy() {
        super.onDestroy()
        releaseMP()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        chbLoop.setOnCheckedChangeListener { _, isChecked ->
            if (mediaPlayer != null) {
                mediaPlayer?.isLooping = isChecked
            }
        }

    }

    fun onClickStart(view: View) {
        releaseMP()
        try {
            when (view.id) {
                R.id.btnStartHttp -> {
                    Log.d(LOG_TAG, "start HTTP")
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setDataSource(DATA_HTTP)
                    mediaPlayer?.setAudioAttributes(
                        AudioAttributes.Builder().setUsage(USAGE_MEDIA).setContentType(
                            CONTENT_TYPE_MUSIC
                        ).build()
                    )
                    Log.d(LOG_TAG, "prepareAsync")
                    mediaPlayer?.setOnPreparedListener(this)
                    mediaPlayer?.prepareAsync()
                }
                R.id.btnStartStream -> {
                    Log.d(LOG_TAG, "start Stream")
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setDataSource(DATA_STREAM)
                    mediaPlayer?.setAudioAttributes(
                        AudioAttributes.Builder().setUsage(USAGE_MEDIA).setContentType(
                            CONTENT_TYPE_MUSIC
                        ).build()
                    )
                    Log.d(LOG_TAG, "prepareAsync")
                    mediaPlayer?.setOnErrorListener(this)
                    mediaPlayer?.setOnPreparedListener(this)
                    mediaPlayer?.prepareAsync()
                }
                R.id.btnStartSD -> {
                    Log.d(LOG_TAG, "start SD")
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setDataSource(DATA_SD)
                    mediaPlayer?.setAudioAttributes(
                        AudioAttributes.Builder().setUsage(USAGE_MEDIA).setLegacyStreamType(
                            STREAM_MUSIC
                        ).setContentType(
                            CONTENT_TYPE_MUSIC
                        ).build()
                    )
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                }
                R.id.btnStartUri -> {
                    Log.d(LOG_TAG, "start URI")
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setDataSource(this, DATA_URI)
                    mediaPlayer?.setAudioAttributes(
                        AudioAttributes.Builder().setUsage(USAGE_MEDIA).setLegacyStreamType(
                            STREAM_MUSIC
                        ).setContentType(
                            CONTENT_TYPE_MUSIC
                        ).build()
                    )
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                }
                R.id.btnStartRaw -> {
                    Log.d(LOG_TAG, "start Raw")
                    mediaPlayer = MediaPlayer.create(this, R.raw.explosion)
                    mediaPlayer?.start()
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        if (mediaPlayer == null) {
            return
        }
        mediaPlayer?.isLooping = chbLoop.isChecked
        mediaPlayer?.setOnCompletionListener(this)
    }

    private fun releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer?.release()
                mediaPlayer = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onClick(view: View) {
        if (mediaPlayer == null) return
        when (view.id) {
            R.id.btnPause -> if (mediaPlayer?.isPlaying == true) mediaPlayer?.pause()
            R.id.btnResume -> if (mediaPlayer?.isPlaying == false) mediaPlayer?.start()
            R.id.btnStop -> mediaPlayer?.stop()
            R.id.btnBackward -> mediaPlayer?.seekTo(mediaPlayer?.currentPosition ?: 3000 - 3000)
            R.id.btnForward -> mediaPlayer?.seekTo(mediaPlayer?.currentPosition ?: 0 + 3000)
            R.id.btnInfo -> {
                Log.d(LOG_TAG, "Playing " + mediaPlayer?.isPlaying)
                Log.d(
                    LOG_TAG, "Time " + mediaPlayer?.currentPosition + " / "
                            + mediaPlayer?.duration
                )
                Log.d(LOG_TAG, "Looping " + mediaPlayer?.isLooping)
                Log.d(
                    LOG_TAG,
                    "Volume " + am.getStreamVolume(STREAM_MUSIC)
                )
            }

        }
    }


    override fun onPrepared(mp: MediaPlayer?) {
        Log.d(LOG_TAG, "onPrepared");
        mp?.start()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        Log.d(LOG_TAG, "onCompletion");    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.d(LOG_TAG, "what $what  extra $extra")
        return true
    }
}
