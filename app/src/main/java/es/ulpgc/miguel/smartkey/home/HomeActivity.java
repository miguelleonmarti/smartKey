package es.ulpgc.miguel.smartkey.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

  // declaring the login button and the edit text
  private Button logoutButton, profileButton;
  private TextView nameText; // todo quitar

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
    nameText = findViewById(R.id.nameText);

    // home adapter
    homeAdapter = new HomeAdapter();

    // declaring the recyclerView, finding its id and changing its adapter
    RecyclerView recyclerView = findViewById(R.id.doorList);
    recyclerView.setAdapter(homeAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    // listeners when logout button is clicked
    logoutButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // todo y quitar
        nameText.setText(firebaseAuth.getCurrentUser().getDisplayName());
      }
    });

    // listener when profile button is clicked
    profileButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO
      }
    });

    // do the setup
    HomeScreen.configure(this);
  }



  @Override
  protected void onResume() {
    super.onResume();

    // do some work
    presenter.fetchData();
  }

  @Override
  public void injectPresenter(HomeContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(HomeViewModel viewModel) {

  }
}
