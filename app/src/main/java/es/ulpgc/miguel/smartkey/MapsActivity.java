package es.ulpgc.miguel.smartkey;

import androidx.fragment.app.FragmentActivity;
import es.ulpgc.miguel.smartkey.models.Door;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

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
    mapFragment.getMapAsync(this);

    Bundle bundle = getIntent().getExtras();
    doorList = (ArrayList<Door>) bundle.getSerializable("doorList");
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
  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    // Add a marker in Sydney and move the camera todo comentar bien

    for (Door door: doorList) {
      LatLng coordinates = new LatLng(door.getLatitude(), door.getLongitude());
      mMap.addMarker(new MarkerOptions().position(coordinates).title(door.getName()));
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 17));
    }


  }
}
