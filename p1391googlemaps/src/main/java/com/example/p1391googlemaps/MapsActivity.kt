package com.example.p1391googlemaps

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val TAG = "myLogs"
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        btnTest.setOnClickListener {
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(27.0, 33.0))
                .zoom(5f)
                .bearing(45f)
                .tilt(20f)
                .build()
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(
                LatLngBounds(LatLng(-39.0, 112.0), LatLng(-11.0, 154.0)),
            100)
            mMap.animateCamera(cameraUpdate)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        init()
    }

    private fun init() {
        mMap.setOnMapClickListener {
            Log.d(TAG, "onMapClick: " + it.latitude + "," + it.longitude)
        }
        mMap.setOnMapLongClickListener {
            Log.d(TAG, "onMapLongClick: " + it.latitude + "," + it.longitude)
        }
        mMap.setOnCameraMoveStartedListener { reason: Int ->
            when (reason) {
                GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE -> {
                    Log.d(TAG, "The user gestured on the map.")
                }
                GoogleMap.OnCameraMoveStartedListener
                    .REASON_API_ANIMATION -> {
                    Log.d(TAG, "The user tapped something on the map.")
                }
                GoogleMap.OnCameraMoveStartedListener
                    .REASON_DEVELOPER_ANIMATION -> {
                    Log.d(TAG, "The app moved the camera.")
                }
            }
        }
        mMap.setOnCameraIdleListener {
            val midLatLng: LatLng = mMap.cameraPosition.target//map's center position latitude & longitude
            Log.d(TAG, "onCameraChange: " + midLatLng.latitude + "," + midLatLng.longitude)
        }

    }
}
