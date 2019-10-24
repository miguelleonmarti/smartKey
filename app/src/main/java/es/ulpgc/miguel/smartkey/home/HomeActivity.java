package es.ulpgc.miguel.smartkey.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.ulpgc.miguel.smartkey.R;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

  public static String TAG = HomeActivity.class.getSimpleName();

  private HomeContract.Presenter presenter;

  // declaring the buttons
  private Button logoutButton, mapButton;

  // declaring the adapter
  private HomeAdapter homeAdapter;

  // bluetooth
  private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  private BluetoothAdapter bluetoothAdapter;
  private static final int MESSAGE_WRITE = 0;
  private static final int MESSAGE_TOAST = 1;
  Handler handler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(@NonNull Message message) {
      switch (message.what) {
        case MESSAGE_WRITE:
        case MESSAGE_TOAST:
          break;
      }
      return false;
    }
  });

  // declaring location manager
  private LocationManager locationManager;
  private LocationListener locationListener;

  @SuppressLint("MissingPermission") // todo comprobar permisos
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    // location service
    locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    locationListener = new LocationListener() {
      @Override
      public void onLocationChanged(Location location) {
        presenter.fetchDoors(location);
      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {

      }

      @Override
      public void onProviderEnabled(String provider) {

      }

      @Override
      public void onProviderDisabled(String provider) {

      }
    };
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 2, locationListener);

    // hiding the action bar
    getSupportActionBar().hide();

    // bluetooth
    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    // initializing the components of the view
    logoutButton = findViewById(R.id.logoutButton);
    mapButton = findViewById(R.id.mapButton);

    // todo: pidiendo permiso (no sé si funciona)
    ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

    // home adapter
    homeAdapter = new HomeAdapter(new RecyclerViewOnClick() {
      @Override
      public void onClick(String address) {
        new ConnectThread(bluetoothAdapter.getRemoteDevice(address), bluetoothAdapter, MY_UUID, new BluetoothContract.ConnectThread() {
          @Override
          public void onSocketConnected(BluetoothSocket socket) {
            String message = "todo correcto"; // todo aqui iria el id del usuario
            ConnectedThread connectedThread = new ConnectedThread(socket, handler);
            connectedThread.write(message.getBytes());
            connectedThread.cancel();

          }
        }).start();

        //presenter.openDoor(address); todo quite este metodo porque va en la app de la puerta
      }
    });

    // declaring the recyclerView, finding its id and changing its adapter
    RecyclerView recyclerView = findViewById(R.id.doorList);
    recyclerView.setAdapter(homeAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    // listeners when logout button is clicked
    logoutButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        presenter.signOut();
      }
    });

    mapButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        presenter.startMapsScreen();
      }
    });

    // do the setup
    HomeScreen.configure(this);
  }

  @Override
  public void injectPresenter(HomeContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(HomeViewModel viewModel) {
    // toast with logout message
    if (!viewModel.getMessage().equals("")) {
      Toast.makeText(getApplicationContext(), viewModel.getMessage(), Toast.LENGTH_LONG).show();
    }
    // adapter sets the list items
    homeAdapter.setItems(viewModel.getDoorList());
  }
}
