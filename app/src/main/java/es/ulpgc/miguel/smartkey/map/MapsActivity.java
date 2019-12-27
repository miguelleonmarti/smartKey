package es.ulpgc.miguel.smartkey.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import es.ulpgc.miguel.smartkey.R;
import es.ulpgc.miguel.smartkey.models.Door;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

  private GoogleMap mMap;

  private ArrayList<Door> doorList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);

    // todo pidiendo permisos
    //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

    // retrieving the door list in order to show the doors' location on the map
    Bundle bundle = getIntent().getExtras();
    doorList = (ArrayList<Door>) bundle.getSerializable("doorList");

    // getting the map
    mapFragment.getMapAsync(this);
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

  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode) {
      case 1: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
          // permission denied, boo! Disable the
          // functionality that depends on this permission.
        }
        return;
      }
      // other 'case' lines to check for other
      // permissions this app might request
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    // Add a marker in each door

    for (Door door: doorList) {
      LatLng coordinates = new LatLng(Float.parseFloat(door.getLatitude()), Float.parseFloat(door.getLongitude()));
      if (door.isOpen()) {
        // green marker if open
        mMap.addMarker(new MarkerOptions().position(coordinates).title(door.getName())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
      } else {
        // red marker if closed
        mMap.addMarker(new MarkerOptions().position(coordinates).title(door.getName()).
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
      }

    }

    mMap.setMyLocationEnabled(true); // enabling my location

    mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

      @Override
      public void onMyLocationChange(Location location) {
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));
      }
    });

  }

}

