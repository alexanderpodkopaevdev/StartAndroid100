package com.example.p1411canvasview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    class DrawView(ctx: Context) : SurfaceView(ctx), SurfaceHolder.Callback {
        private lateinit var drawThread: DrawThread

        class DrawThread(var surfaceHolder: SurfaceHolder) : Thread() {
            var running = false

            override fun run() {
                var canvas: Canvas?
                while (running) {
                    canvas = null
                    try {
                        canvas = surfaceHolder.lockCanvas(null)
                        if (canvas == null) {
                            continue
                        }
                        canvas.drawColor(Color.GREEN)
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas)
                        }
                    }
                }
            }
        }

        init {
            holder.addCallback(this)
        }

        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            var retry = true
            drawThread.running = false
            while (retry) {
                try {
                    drawThread.join()
                    retry = false
                } catch (e: InterruptedException) {

                }
            }
        }

        override fun surfaceCreated(holder: SurfaceHolder?) {
            drawThread = DrawThread(getHolder())
            drawThread.running = true
            drawThread.start()
        }


    }
}
