package com.example.p1381location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    val PERMISSION_ASK = 123
    private lateinit var locationManager: LocationManager
    val sbGPS = StringBuilder()
    val sbNet = StringBuilder()
    val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            showLocation(location)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            if (provider == LocationManager.GPS_PROVIDER) {
                tvStatusGPS.text = "Status $status"
            } else if (provider == LocationManager.NETWORK_PROVIDER) {
                tvStatusNet.text = "Status $status"
            }

        }

        override fun onProviderEnabled(provider: String?) {
            checkEnabled()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    showLocation(locationManager.getLastKnownLocation(provider))
                } else {
                    askForLocalPermissions()
                }
            } else {
                showLocation(locationManager.getLastKnownLocation(provider))
            }
        }

        override fun onProviderDisabled(provider: String?) {
            checkEnabled()
        }

    }

    private fun checkEnabled() {
        tvEnabledGPS.text = ("Enabled: "
                + locationManager
            .isProviderEnabled(LocationManager.GPS_PROVIDER))
        tvEnabledNet.text = ("Enabled: "
                + locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    private fun showLocation(location: Location?) {
        if (location == null) return
        if (location.provider == LocationManager.GPS_PROVIDER) {
            tvLocationGPS.text = formatLocation(location)
        } else if (location.provider == LocationManager.NETWORK_PROVIDER) {
            tvLocationNet.text = formatLocation(location)
        }
    }

    private fun formatLocation(location: Location): CharSequence = String.format(
        "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3\$tF %3\$tT",
        location.latitude,
        location.longitude,
        Date(location.time)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        btnLocationSettings.setOnClickListener {
            startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000L * 10,
                    10f,
                    locationListener
                )
                if (locationManager.allProviders.contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER
                    )
                ) {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        1000L * 10,
                        10f,
                        locationListener
                    )
                }

            } else {
                askForLocalPermissions()
            }
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000L * 10,
                10f,
                locationListener
            )
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000L * 10,
                10f,
                locationListener
            )
        }


        checkEnabled()
    }

    private fun askForLocalPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        requestPermissions(permissions, PERMISSION_ASK)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_ASK -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // all requested permissions were granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            1000L * 10,
                            10f,
                            locationListener
                        )
                    }
                }
            } else {
                askForLocalPermissions()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
    }
}
