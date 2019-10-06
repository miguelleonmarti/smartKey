package es.ulpgc.miguel.smartkey.register;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.miguel.smartkey.R;

public class RegisterActivity
    extends AppCompatActivity implements RegisterContract.View {

  public static String TAG = RegisterActivity.class.getSimpleName();

  private RegisterContract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    // do the setup
    RegisterScreen.configure(this);
  }

  @Override
  protected void onResume() {
    super.onResume();

    // do some work
    presenter.fetchData();
  }

  @Override
  public void injectPresenter(RegisterContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(RegisterViewModel viewModel) {
    //Log.e(TAG, "displayData()");

    // deal with the data
  }
}
