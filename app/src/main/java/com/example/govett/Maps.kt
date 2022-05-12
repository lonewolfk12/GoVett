package com.example.govett


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.type.LatLng
import kotlinx.android.synthetic.main.activity_maps.*

class Maps : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
//private lateinit var  fusedLocationClient:FusedLocationProviderClient
private lateinit var map:GoogleMap

companion object {
    const val REQUEST_CODE_LOCATION = 0
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        createFragment()
       /* Bntcita.setOnClickListener{
            Toast.makeText(this, "Cita Creada",Toast.LENGTH_SHORT).show();

        }
        Bntcancelar.setOnClickListener {
            Toast.makeText(this, "Cita Cancelada", Toast.LENGTH_SHORT).show()
        }*/
    }

    private fun createFragment() {
      val mapFragment=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        createMarker()
        enableLocation()
        createPolylines()
        onMarkerClick()

    }

    private fun onMarkerClick() {
        map.setOnMarkerClickListener(this)
        map.uiSettings.isZoomControlsEnabled=true
    }

    private fun createMarker() {
         val coordinates = com.google.android.gms.maps.model.LatLng(19.251006, -99.642006)
        val marker=MarkerOptions().position(coordinates).title("Universidad")
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))


        map.addMarker(marker)
        map.animateCamera(
        CameraUpdateFactory.newLatLngZoom(coordinates,20f),
        2000,
        null
        )


    }
    private fun isLocationPermissionGranted() =ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION)==
            PackageManager.PERMISSION_GRANTED

private fun enableLocation(){
    if(!::map.isInitialized)return
    if(isLocationPermissionGranted()){
        map.isMyLocationEnabled = true
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {


            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }


    }else{
        requestLocationPermission()


    }
}
private fun requestLocationPermission(){
    if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
        Toast.makeText(this, "Dirigirse a los ajustes y acepta los permisos de localización", Toast.LENGTH_SHORT).show()
}
    else{

    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE_LOCATION)
}
}

   override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults)
       when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true

                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }

            }else{
                Toast.makeText(this, "Para activar la localización ve ajustes y acepta los permisos de localización", Toast.LENGTH_SHORT).show()


            }else ->{}


        }
    }


    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized)return

        if(!isLocationPermissionGranted()){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map.isMyLocationEnabled=false
            Toast.makeText(this, "Para activar la localización ve ajustes y acepta los permisos de localización", Toast.LENGTH_SHORT).show()

        }
    }


    private fun createPolylines(){
        val polylineOptions = PolylineOptions()
            .add(com.google.android.gms.maps.model.LatLng(19.251006, -99.642006))
            .add(com.google.android.gms.maps.model.LatLng(19.271122, -99.645567))


        val  polyline=map.addPolyline(polylineOptions)

        val pattern= listOf(
        Dot(), Gap(10f)

        )
        polyline.pattern=pattern
        polyline.endCap=CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.circle_48px))


    }

    override fun onMarkerClick(p0: Marker): Boolean {
return false
    }

}