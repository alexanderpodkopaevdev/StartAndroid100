package com.example.p1371sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var sensorManager: SensorManager
    var sensors: List<Sensor> = listOf()
    lateinit var sensorLight: Sensor

    private val listenerLight = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null)
            tvText.text = event.values[0].toString()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        btnSensList.setOnClickListener {
            sensorManager.unregisterListener(listenerLight, sensorLight)
            val sb = StringBuilder()
            for (sensor in sensors) {
                sb.append("name = ").append(sensor.name)
                    .append(", type = ").append(sensor.type)
                    .append("\nvendor = ").append(sensor.vendor)
                    .append(" ,version = ").append(sensor.version)
                    .append("\nmax = ").append(sensor.maximumRange)
                    .append(", resolution = ").append(sensor.resolution)
                    .append("\n--------------------------------------\n")
            }
            tvText.text = sb
        }

        btnSensLight.setOnClickListener {
            sensorManager.registerListener(listenerLight,sensorLight,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(listenerLight, sensorLight)
    }
}
