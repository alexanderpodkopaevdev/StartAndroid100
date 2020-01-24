package com.example.p1311cameraintent

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    companion object {
        const val TYPE_PHOTO = 1
        const val TYPE_VIDEO = 2

        const val REQUEST_CODE_PHOTO = 1
        const val REQUEST_CODE_VIDEO = 2

        const val TAG = "myLogs"
    }
    lateinit var directory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createDirectory()
        btnPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //intent.putExtra(MediaStore.EXTRA_OUTPUT,generateFileUri(TYPE_PHOTO))
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }
        btnVideo.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            //intent.putExtra(MediaStore.EXTRA_OUTPUT,generateFileUri(TYPE_VIDEO))
            startActivityForResult(intent, REQUEST_CODE_VIDEO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null")
                } else {
                    Log.d(TAG, "Photo uri: ${intent.data}")
                    val bundle = intent.extras
                    if (bundle!=null) {
                        val obj = bundle.get("data")
                        if (obj is Bitmap) {
                            Log.d(TAG,"bitmap = ${obj.width} x ${obj.height}")
                            ivPhoto.setImageBitmap(obj)
                        }
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled")
            }
        }
        if (requestCode == REQUEST_CODE_VIDEO) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null")
                } else {
                    Log.d(TAG, "Video uri: ${intent.data}")
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled")
            }
        }
        super.onActivityResult(requestCode, resultCode, intent)
    }

    private fun generateFileUri(type: Int): Uri {
        val file = when (type) {
            TYPE_PHOTO -> {
                File(directory.path+"/photo_" + System.currentTimeMillis() + ".jpg")
            }
            TYPE_VIDEO -> {
                File(directory.path+"/video_" + System.currentTimeMillis() + ".mp4")
            }
            else -> File(directory.path+"/video_" + System.currentTimeMillis() + ".mp4")
        }
        Log.d(TAG, "fileName = $file")
        return FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID + ".provider",file)
    }


    private fun createDirectory() {
        directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MyFolder")
        if (!directory.exists()) {
            directory.mkdirs()
        }

    }
}
