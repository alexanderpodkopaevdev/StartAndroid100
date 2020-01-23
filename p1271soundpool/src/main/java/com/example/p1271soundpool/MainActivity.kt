package com.example.p1271soundpool

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


class MainActivity : AppCompatActivity(), SoundPool.OnLoadCompleteListener {

    val LOG_TAG = "myLogs"
    val MAX_STREAMS = 5

    lateinit var sp: SoundPool
    var soundIdShot = 0
    var soundIdExplosion = 0


    var streamIDShot = 0
    var streamIDExplosion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val attribute = AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setLegacyStreamType(AudioManager.STREAM_MUSIC).build()
        sp = SoundPool.Builder().setMaxStreams(MAX_STREAMS).setAudioAttributes(attribute).build()
        sp.setOnLoadCompleteListener(this)

        soundIdShot = sp.load(this,R.raw.shot,1)
        Log.d(LOG_TAG, "soundIdShot = $soundIdShot")
        try {
            soundIdExplosion = sp.load(assets.openFd("explosion.ogg"),1)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        Log.d(LOG_TAG, "soundIdExplosion = $soundIdExplosion")
        btnPlay.setOnClickListener {
            sp.play(soundIdShot, 0.1f, 1f, 0, 5, 1f)
            sp.play(soundIdExplosion, 1f, 0.1f, 0, 2, 1f)
        }
    }

    override fun onLoadComplete(soundPool: SoundPool?, sampleId: Int, status: Int) {
        Log.d(LOG_TAG, "onLoadComplete, sampleId = $sampleId, status = $status")    }
}
