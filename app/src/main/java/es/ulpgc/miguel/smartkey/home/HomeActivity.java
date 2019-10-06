package es.ulpgc.miguel.smartkey.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.miguel.smartkey.R;

public class HomeActivity
    extends AppCompatActivity implements HomeContract.View {

  public static String TAG = HomeActivity.class.getSimpleName();

  private HomeContract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

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
