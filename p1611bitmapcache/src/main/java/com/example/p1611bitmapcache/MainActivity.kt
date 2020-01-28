package com.example.p1611bitmapcache

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTERNAL_REQUEST = 138

        val EXTERNAL_PERMS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }


    private fun requestForPermission(): Boolean {
        var isPermissionOn = true
        val version = Build.VERSION.SDK_INT
        if (version >= 23) {
            if (!canAccessExternalSd()) {
                isPermissionOn = false
                requestPermissions(EXTERNAL_PERMS, EXTERNAL_REQUEST)
            }
        }
        return isPermissionOn
    }

    private fun canAccessExternalSd(): Boolean {
        return hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun hasPermission(perm: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestForPermission()
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "/L0161/"
        )

        val filesArray= dir.listFiles()
        if (filesArray != null) {
            for (file in filesArray) {
                Log.d("myTAG", file.absolutePath.toString())
            }
            val imageAdapter = ImageAdapter(this, filesArray)
            lvImages.adapter = imageAdapter
        }
    }
}


class ImageAdapter(ctx: Context, filesArray: Array<File>) :
    ArrayAdapter<File>(ctx, R.layout.list_item, filesArray) {
    private var mInflater = LayoutInflater.from(ctx)
    private var mSize = ctx.resources.getDimensionPixelSize(R.dimen.image_size)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: mInflater.inflate(R.layout.list_item, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val bitmap = getBitmap(position)
        imageView?.setImageBitmap(bitmap)
        return view
    }

    private fun getBitmap(position: Int): Bitmap {
        val defaultPath = File(
            Environment.getExternalStorageDirectory(),
            "Download/L0161/image_10.jpg"
        ).absolutePath
        val path = getItem(position)?.absolutePath ?: defaultPath

        return Utils.decodeSampledBitmapFromResource(path, mSize, mSize)

    }

}
