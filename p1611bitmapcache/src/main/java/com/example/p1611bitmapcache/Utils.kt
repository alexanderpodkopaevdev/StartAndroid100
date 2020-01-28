package com.example.p1611bitmapcache

import android.graphics.Bitmap
import android.graphics.BitmapFactory

object Utils {
    fun decodeSampledBitmapFromResource(path: String?, reqWidth: Int, reqHeight: Int) : Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path,options)

        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight)

        options.inJustDecodeBounds = false
        options.inPreferredConfig = Bitmap.Config.RGB_565
        return BitmapFactory.decodeFile(path,options)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        var inSimpleSize = 1

        if (options.outHeight > reqHeight || options.outWidth > reqWidth) {
            val halfHeight = options.outHeight / 2
            val halfWidth = options.outWidth / 2

            while ((halfHeight / inSimpleSize) > reqHeight &&
                (halfWidth / inSimpleSize) > reqWidth) {
                inSimpleSize *= 2
            }
        }
        return inSimpleSize
    }
}