package es.ulpgc.miguel.smartkey.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.ulpgc.miguel.smartkey.R;

public class HomeActivity
    extends AppCompatActivity implements HomeContract.View {

  public static String TAG = HomeActivity.class.getSimpleName();

  private HomeContract.Presenter presenter;

  // declaring an instance of FirebaseAuth (to login)
  private FirebaseAuth firebaseAuth;

  // declaring the buttons
  private Button logoutButton, profileButton;

  private ImageView logo; // todo: prueba para acceder al mapa

  // declaring the adapter
  private HomeAdapter homeAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    // hiding the action bar
    getSupportActionBar().hide();

    // getting the FirebaseAuth instance
    firebaseAuth = FirebaseAuth.getInstance();

    // initializing the components of the view
    logoutButton = findViewById(R.id.logoutButton);
    profileButton = findViewById(R.id.profileButton);
    logo = findViewById(R.id.logo); // todo prueba para acceder al mapa

    // home adapter
    homeAdapter = new HomeAdapter(new RecyclerViewOnClick() {
      @Override
      public void onClick(int doorId) {
        presenter.openDoor(doorId);
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

    // listener when profile button is clicked
    profileButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // TODO: pasar a la pantalla profile
      }
    });

    // todo prueba para acceder al mapa
    logo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        presenter.startMapsScreen(); // todo prueba para acceder al mapa
      }
    });

    // do the setup
    HomeScreen.configure(this);
  }



  @Override
  protected void onResume() {
    super.onResume();
    presenter.fetchDoors();
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
