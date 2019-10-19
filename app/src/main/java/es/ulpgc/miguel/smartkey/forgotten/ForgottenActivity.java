package es.ulpgc.miguel.smartkey.forgotten;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.miguel.smartkey.R;
import es.ulpgc.miguel.smartkey.services.Checker;

public class ForgottenActivity
    extends AppCompatActivity implements ForgottenContract.View {

  public static String TAG = ForgottenActivity.class.getSimpleName();

  private ForgottenContract.Presenter presenter;

  // declaring the buttons and the inputs
  private Button sendButton;
  private EditText emailInput;

  // declaring inputs checker
  private Checker checker;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgotten);

    // hiding the action bar
    getSupportActionBar().hide();

    // initializing the components of the view
    sendButton = findViewById(R.id.sendButton);
    emailInput = findViewById(R.id.emailInput);

    // initializing the checker
    checker = new Checker();

    // listener when send email button is clicked
    sendButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (checker.validateEmail(emailInput)) {
          String email = emailInput.getText().toString();
          presenter.sendRecoveryEmail(email);
        }
      }
    });

    // do the setup
    ForgottenScreen.configure(this);
  }

  @Override
  protected void onResume() {
    super.onResume();

  }

  @Override
  public void injectPresenter(ForgottenContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(ForgottenViewModel viewModel) {
    Toast.makeText(this, viewModel.getMessage(), Toast.LENGTH_SHORT).show();
  }
}
