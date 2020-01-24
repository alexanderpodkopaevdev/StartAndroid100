package com.example.p1281audiofocus

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException


class MainActivity : AppCompatActivity(), MediaPlayer.OnCompletionListener {

    val LOG_TAG = "myLogs"

    lateinit var audioManager: AudioManager

    var afListenerMusic: OnAudioFocusChangeListener? = null
    var afListenerSound: OnAudioFocusChangeListener? = null
    var AudioFocusRequestMusic: AudioFocusRequest? = null
    var AudioFocusRequestSound: AudioFocusRequest? = null


    var mpMusic: MediaPlayer? = null
    var mpSound: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    fun onClickMusic(view: View?) {
        mpMusic = MediaPlayer()
        try {
            mpMusic = MediaPlayer.create(this, R.raw.explosion)
            mpMusic?.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mpMusic!!.setOnCompletionListener(this)
        afListenerMusic = AFListener(mpMusic, "Music")
        val listener = OnAudioFocusChangeListener { focusChange ->
            var event = ""
            when (focusChange) {
                AudioManager.AUDIOFOCUS_LOSS -> {
                    event = "AUDIOFOCUS_LOSS"
                    mpMusic!!.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    event = "AUDIOFOCUS_LOSS_TRANSIENT"
                    mpMusic!!.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    event = "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK"
                    mpMusic!!.setVolume(0.5f, 0.5f)
                }
                AudioManager.AUDIOFOCUS_GAIN -> {
                    event = "AUDIOFOCUS_GAIN"
                    if (!mpMusic!!.isPlaying) mpMusic!!.start()
                    mpMusic!!.setVolume(1.0f, 1.0f)
                }
            }
            Log.d(LOG_TAG, "Music onAudioFocusChange: $event")
        }

        val requestResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mPlaybackAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            AudioFocusRequestMusic =
                AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).setAudioAttributes(
                    mPlaybackAttributes
                )
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(listener)
                    .build()
            audioManager.requestAudioFocus(AudioFocusRequestMusic!!)
        } else {
            audioManager.requestAudioFocus(
                listener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }
        Log.d(LOG_TAG, "Music request focus, result: $requestResult")
        mpMusic!!.start()
    }

    fun onClickSound(view: View) {
        var durationHint = AudioManager.AUDIOFOCUS_GAIN
        when (view.id) {
            R.id.btnPlaySoundG -> durationHint = AudioManager.AUDIOFOCUS_GAIN
            R.id.btnPlaySoundGT -> durationHint = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            R.id.btnPlaySoundGTD -> durationHint = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
        }
        mpSound = MediaPlayer.create(this, R.raw.shot)
        mpSound!!.setOnCompletionListener(this)
        afListenerSound = AFListener(mpSound, "Sound")

        val requestResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mPlaybackAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            AudioFocusRequestSound = AudioFocusRequest.Builder(durationHint).setAudioAttributes(
                mPlaybackAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener(afListenerMusic as OnAudioFocusChangeListener)
                .build()
            audioManager.requestAudioFocus(AudioFocusRequestSound!!)
        } else {
            audioManager.requestAudioFocus(
                afListenerMusic,
                AudioManager.STREAM_MUSIC,
                durationHint
            )
        }
        Log.d(LOG_TAG, "Sound request focus, result: $requestResult")
        mpSound!!.start()
    }

    override fun onCompletion(mp: MediaPlayer) {
        if (mp === mpMusic) {
            Log.d(LOG_TAG, "Music: abandon focus")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                audioManager.abandonAudioFocusRequest(AudioFocusRequestMusic!!)
            } else {
                audioManager.abandonAudioFocus(afListenerMusic)
            }

        } else if (mp === mpSound) {
            Log.d(LOG_TAG, "Sound: abandon focus")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                audioManager.abandonAudioFocusRequest(AudioFocusRequestSound!!)
            } else {
                audioManager.abandonAudioFocus(afListenerSound)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mpMusic != null) mpMusic!!.release()
        if (mpSound != null) mpSound!!.release()
        if (afListenerMusic != null) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.abandonAudioFocusRequest(AudioFocusRequestMusic!!)
        } else {
            audioManager.abandonAudioFocus(afListenerMusic)
        }
        if (afListenerSound != null) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.abandonAudioFocusRequest(AudioFocusRequestSound!!)
        } else {
            audioManager.abandonAudioFocus(afListenerSound)
        }
    }

    inner class AFListener(var mp: MediaPlayer?, var label: String) :
        OnAudioFocusChangeListener {

        override fun onAudioFocusChange(focusChange: Int) {
            var event = ""
            when (focusChange) {
                AudioManager.AUDIOFOCUS_LOSS -> {
                    event = "AUDIOFOCUS_LOSS"
                    mp!!.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    event = "AUDIOFOCUS_LOSS_TRANSIENT"
                    mp!!.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    event = "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK"
                    mp!!.setVolume(0.5f, 0.5f)
                }
                AudioManager.AUDIOFOCUS_GAIN -> {
                    event = "AUDIOFOCUS_GAIN"
                    if (!mp!!.isPlaying) mp!!.start()
                    mp!!.setVolume(1.0f, 1.0f)
                }
            }
            Log.d(LOG_TAG, "$label onAudioFocusChange: $event")
        }

    }
}
