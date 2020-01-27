package com.example.p1391googlemaps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
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
            mMap.addMarker(MarkerOptions().position(LatLng(-10.0,-10.0)).title("Hello").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            ))
            mMap.addMarker(MarkerOptions().position(LatLng(0.0,0.0)).title("Hello2").icon(
                BitmapDescriptorFactory.defaultMarker()
            ))
            mMap.addMarker(MarkerOptions().position(LatLng(10.0,10.0)).title("Hello3").icon(
                bitmapDescriptorFromVector(this, R.drawable.ic_android_black_24dp)
            ))
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
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
        val list: MutableList<LatLng> = ArrayList()
        list.add(LatLng((-4).toDouble(), (-5).toDouble()))
        list.add(LatLng(0.0, (-1).toDouble()))
        list.add(LatLng(4.0, (-5).toDouble()))
        list.add(LatLng(0.0, (-9).toDouble()))

        val polygoneOptions = PolygonOptions()
            .add(LatLng((-5).toDouble(), (-10).toDouble()))
            .add(LatLng((-5).toDouble(), 0.0))
            .add(LatLng(5.0, 0.0))
            .add(LatLng(5.0, (-10).toDouble()))
            .addHole(list)
            .strokeColor(Color.CYAN).strokeWidth(1f)
            .fillColor(Color.GREEN)

        mMap.addPolygon(polygoneOptions)
        /*mMap.setOnMapClickListener {
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
        }*/

    }
}
